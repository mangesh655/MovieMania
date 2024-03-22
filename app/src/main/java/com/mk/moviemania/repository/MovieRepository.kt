package com.mk.moviemania.repository

import androidx.paging.PagingSource
import com.mk.moviemania.common.theme.AppTheme
import com.mk.moviemania.navigation.feed.list.MovieList
import com.mk.moviemania.navigation.feed.ui.FeedView
import com.mk.moviemania.network.model.MovieResponse
import com.mk.moviemania.network.model.Result
import com.mk.moviemania.persistence.database.entity.MovieDb
import com.mk.moviemania.persistence.MovieDbMini
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    val currentMovieList: Flow<MovieList>

    val currentFeedView: Flow<FeedView>

    fun moviesPagingSource(pagingKey: String): PagingSource<Int, MovieDb>

    fun moviesFlow(pagingKey: String, limit: Int): Flow<List<MovieDb>>

    suspend fun moviesResult(movieList: String, page: Int): Result<MovieResponse>

    suspend fun movie(pagingKey: String, movieId: Int): MovieDb

    suspend fun movieDetails(pagingKey: String, movieId: Int): MovieDb

    suspend fun moviesWidget(): List<MovieDbMini>

    suspend fun removeMovies(pagingKey: String)

    suspend fun removeMovie(pagingKey: String, movieId: Int)

    suspend fun insertMovies(pagingKey: String, page: Int, movies: List<MovieResponse>)

    suspend fun insertMovie(pagingKey: String, movie: MovieDb)

    suspend fun updateMovieColors(movieId: Int, containerColor: Int, onContainerColor: Int)

    suspend fun selectMovieList(movieList: MovieList)
}