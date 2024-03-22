package com.mk.moviemania.interactor.impl

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.mk.moviemania.common.theme.AppTheme
import com.mk.moviemania.dispatchers.MoviesDispatchers
import com.mk.moviemania.interactor.MovieInteractor
import com.mk.moviemania.interactor.ktx.nameOrLocalList
import com.mk.moviemania.interactor.remote.FeedMoviesRemoteMediator
import com.mk.moviemania.navigation.feed.list.MovieList
import com.mk.moviemania.navigation.feed.ui.FeedView
import com.mk.moviemania.network.model.MovieResponse
import com.mk.moviemania.persistence.AppDatabase
import com.mk.moviemania.persistence.database.entity.MovieDb
import com.mk.moviemania.persistence.MovieDbMini
import com.mk.moviemania.repository.MovieRepository
import com.mk.moviemania.repository.PagingKeyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class MovieInteractorImpl @Inject constructor(
    private val dispatchers: MoviesDispatchers,
    private val movieRepository: MovieRepository,
    private val pagingKeyRepository: PagingKeyRepository,
    private val database: AppDatabase
) : MovieInteractor {

    override val currentMovieList: Flow<MovieList> = movieRepository.currentMovieList

    override val currentFeedView: Flow<FeedView> = movieRepository.currentFeedView

    @OptIn(ExperimentalPagingApi::class)
    override fun moviesPagingData(movieList: MovieList): Flow<PagingData<MovieDb>> {
        return Pager(
            config = PagingConfig(
                pageSize = MovieResponse.DEFAULT_PAGE_SIZE,
                enablePlaceholders = true
            ),
            remoteMediator = FeedMoviesRemoteMediator(
                movieRepository = movieRepository,
                pagingKeyRepository = pagingKeyRepository,
                database = database,
                movieList = movieList.name
            ),
            pagingSourceFactory = { movieRepository.moviesPagingSource(movieList.nameOrLocalList) }
        ).flow
    }

    override fun moviesPagingSource(pagingKey: String): PagingSource<Int, MovieDb> {
        return movieRepository.moviesPagingSource(pagingKey)
    }

    override fun moviesFlow(pagingKey: String, limit: Int): Flow<List<MovieDb>> {
        return movieRepository.moviesFlow(pagingKey, limit)
    }

    override suspend fun moviesWidget(): List<MovieDbMini> {
        return withContext(dispatchers.io) { movieRepository.moviesWidget() }
    }

    override suspend fun movie(pagingKey: String, movieId: Int): MovieDb {
        return withContext(dispatchers.io) { movieRepository.movie(pagingKey, movieId) }
    }

    override suspend fun movieDetails(pagingKey: String, movieId: Int): MovieDb {
        return withContext(dispatchers.io) { movieRepository.movieDetails(pagingKey, movieId) }
    }

    override suspend fun removeMovies(pagingKey: String) {
        return withContext(dispatchers.io) { movieRepository.removeMovies(pagingKey) }
    }

    override suspend fun removeMovie(pagingKey: String, movieId: Int) {
        return withContext(dispatchers.io) { movieRepository.removeMovie(pagingKey, movieId) }
    }

    override suspend fun insertMovie(pagingKey: String, movie: MovieDb) {
        return withContext(dispatchers.io) { movieRepository.insertMovie(pagingKey, movie) }
    }

    override suspend fun updateMovieColors(
        movieId: Int,
        containerColor: Int,
        onContainerColor: Int
    ) {
        return withContext(dispatchers.io) {
            movieRepository.updateMovieColors(movieId, containerColor, onContainerColor)
        }
    }

    override suspend fun selectMovieList(movieList: MovieList) {
        withContext(dispatchers.main) {
            movieRepository.selectMovieList(movieList)
        }
    }
}