package com.mk.moviemania.repository

import android.os.Build
import androidx.paging.PagingSource
import com.mk.moviemania.common.theme.AppTheme
import com.mk.moviemania.exceptions.MovieDetailsException
import com.mk.moviemania.exceptions.MoviesUpcomingException
import com.mk.moviemania.localization.LocaleController
import com.mk.moviemania.navigation.feed.list.MovieList
import com.mk.moviemania.navigation.feed.ui.FeedView
import com.mk.moviemania.network.isTmdbApiKeyEmpty
import com.mk.moviemania.network.model.MovieResponse
import com.mk.moviemania.network.model.Result
import com.mk.moviemania.network.movieservice.MovieNetworkService
import com.mk.moviemania.persistence.database.entity.MovieDb
import com.mk.moviemania.persistence.MovieDbMini
import com.mk.moviemania.persistence.database.dao.MovieDao
import com.mk.moviemania.persistence.database.ktx.orEmpty
import com.mk.moviemania.persistence.datastore.MoviesPreferences
import com.mk.moviemania.exceptions.checkApiKeyNotNullException
import com.mk.moviemania.repository.ktx.mapToMovieDb
import com.mk.moviemania.repository.ktx.movieDb
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class MovieRepositoryImpl @Inject constructor(
    private val movieDao: MovieDao,
    private val preferences: MoviesPreferences,
    private val movieNetworkService: MovieNetworkService,
    private val localeController: LocaleController,
) : MovieRepository {

    override val currentMovieList: Flow<MovieList> = preferences.movieListFlow.map { className ->
        MovieList.transform(className ?: MovieList.NowPlaying.toString())
    }

    override val currentFeedView: Flow<FeedView> = preferences.feedViewFlow.map { name ->
        FeedView.transform(name ?: FeedView.FeedList.toString())
    }

    override fun moviesPagingSource(pagingKey: String): PagingSource<Int, MovieDb> {
        return movieDao.pagingSource(pagingKey)
    }

    override fun moviesFlow(pagingKey: String, limit: Int): Flow<List<MovieDb>> {
        return movieDao.moviesFlow(pagingKey, limit)
    }

    override suspend fun moviesResult(movieList: String, page: Int): Result<MovieResponse> {
        if (isTmdbApiKeyEmpty && movieDao.isEmpty(MovieDb.MOVIES_LOCAL_LIST)) {
            checkApiKeyNotNullException()
        }

        return movieNetworkService.movies(
            list = movieList,
            language = localeController.language,
            page = page
        )
    }

    override suspend fun movie(pagingKey: String, movieId: Int): MovieDb {
        return movieDao.movieById(pagingKey, movieId).orEmpty
    }

    override suspend fun movieDetails(pagingKey: String, movieId: Int): MovieDb {
        return try {
            movieDao.movieById(pagingKey, movieId) ?: movieNetworkService.movie(
                movieId,
                localeController.language
            ).mapToMovieDb
        } catch (ignored: Exception) {
            throw MovieDetailsException
        }
    }

    override suspend fun moviesWidget(): List<MovieDbMini> {
        return try {
            val movieResult = movieNetworkService.movies(
                list = MovieList.Upcoming.name,
                language = localeController.language,
                page = 1
            )
            val moviesDb = movieResult.results.mapIndexed { index, movieResponse ->
                movieResponse.movieDb(
                    movieList = MovieDb.MOVIES_WIDGET,
                    position = index.plus(1)
                )
            }
            movieDao.removeMovies(MovieDb.MOVIES_WIDGET)
            movieDao.insertMovies(moviesDb)
            movieDao.moviesMini(MovieDb.MOVIES_WIDGET, MovieResponse.DEFAULT_PAGE_SIZE)
        } catch (ignored: Exception) {
            movieDao.moviesMini(MovieDb.MOVIES_WIDGET, MovieResponse.DEFAULT_PAGE_SIZE).ifEmpty {
                throw MoviesUpcomingException
            }
        }
    }

    override suspend fun removeMovies(pagingKey: String) {
        movieDao.removeMovies(pagingKey)
    }

    override suspend fun removeMovie(pagingKey: String, movieId: Int) {
        movieDao.removeMovie(pagingKey, movieId)
    }

    override suspend fun insertMovies(pagingKey: String, page: Int, movies: List<MovieResponse>) {
        val maxPosition = movieDao.maxPosition(pagingKey) ?: 0
        val moviesDb = movies.mapIndexed { index, movieResponse ->
            movieResponse.mapToMovieDb(
                movieList = pagingKey,
                page = page,
                position = if (maxPosition == 0) index else maxPosition.plus(index).plus(1)
            )
        }
        movieDao.insertMovies(moviesDb)
    }

    override suspend fun insertMovie(pagingKey: String, movie: MovieDb) {
        val maxPosition = movieDao.maxPosition(pagingKey) ?: 0
        movieDao.insertMovie(
            movie.copy(
                movieList = pagingKey,
                dateAdded = System.currentTimeMillis(),
                position = maxPosition.plus(1)
            )
        )
    }

    override suspend fun updateMovieColors(
        movieId: Int,
        containerColor: Int,
        onContainerColor: Int
    ) {
        movieDao.updateMovieColors(movieId, containerColor, onContainerColor)
    }

    override suspend fun selectMovieList(movieList: MovieList) {
        preferences.setMovieList(movieList.toString())
    }
}