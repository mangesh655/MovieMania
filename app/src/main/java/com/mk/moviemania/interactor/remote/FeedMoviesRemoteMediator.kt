@file:OptIn(ExperimentalPagingApi::class)

package com.mk.moviemania.interactor.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.mk.moviemania.exceptions.PageEmptyException
import com.mk.moviemania.network.ktx.isEmpty
import com.mk.moviemania.network.ktx.isPaginationReached
import com.mk.moviemania.network.ktx.nextPage
import com.mk.moviemania.persistence.AppDatabase
import com.mk.moviemania.persistence.database.entity.MovieDb
import com.mk.moviemania.repository.MovieRepository
import com.mk.moviemania.repository.PagingKeyRepository

class FeedMoviesRemoteMediator(
    private val pagingKeyRepository: PagingKeyRepository,
    private val movieRepository: MovieRepository,
    private val database: AppDatabase,
    private val movieList: String
): RemoteMediator<Int, MovieDb>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieDb>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> pagingKeyRepository.prevPage(movieList)
                LoadType.APPEND -> pagingKeyRepository.page(movieList)
            } ?: return MediatorResult.Success(endOfPaginationReached = true)

            val moviesResult = movieRepository.moviesResult(movieList, loadKey)

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    pagingKeyRepository.removePagingKey(movieList)
                    movieRepository.removeMovies(movieList)
                }

                if (moviesResult.isEmpty) {
                    throw PageEmptyException
                }

                pagingKeyRepository.insertPagingKey(movieList, moviesResult.nextPage, moviesResult.totalPages)
                movieRepository.insertMovies(movieList, moviesResult.page, moviesResult.results)
            }

            MediatorResult.Success(endOfPaginationReached = moviesResult.isPaginationReached)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}