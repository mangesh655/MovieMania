package com.mk.moviemania.interactor

import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.mk.moviemania.common.theme.AppTheme
import com.mk.moviemania.navigation.feed.list.MovieList
import com.mk.moviemania.navigation.feed.ui.FeedView
import com.mk.moviemania.persistence.database.entity.MovieDb
import com.mk.moviemania.persistence.MovieDbMini
import kotlinx.coroutines.flow.Flow

interface MovieInteractor {

    val currentMovieList: Flow<MovieList>

    val currentFeedView: Flow<FeedView>

    fun moviesPagingData(movieList: MovieList): Flow<PagingData<MovieDb>>

    fun moviesPagingSource(pagingKey: String): PagingSource<Int, MovieDb>

    fun moviesFlow(pagingKey: String, limit: Int): Flow<List<MovieDb>>

    suspend fun movie(pagingKey: String, movieId: Int): MovieDb

    suspend fun movieDetails(pagingKey: String, movieId: Int): MovieDb

    suspend fun moviesWidget(): List<MovieDbMini>

    suspend fun removeMovies(pagingKey: String)

    suspend fun removeMovie(pagingKey: String, movieId: Int)

    suspend fun insertMovie(pagingKey: String, movie: MovieDb)

    suspend fun updateMovieColors(movieId: Int, containerColor: Int, onContainerColor: Int)

    suspend fun selectMovieList(movieList: MovieList)
}