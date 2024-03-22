package com.mk.moviemania.repository.ktx

import com.mk.moviemania.network.model.Movie
import com.mk.moviemania.network.model.MovieResponse
import com.mk.moviemania.persistence.database.entity.MovieDb

internal fun MovieResponse.mapToMovieDb(movieList: String, page: Int, position: Int): MovieDb {
    return MovieDb(
        movieList = movieList,
        dateAdded = System.currentTimeMillis(),
        page = page,
        position = position,
        movieId = id,
        overview = overview.orEmpty(),
        posterPath = posterPath.orEmpty(),
        backdropPath = backdropPath.orEmpty(),
        releaseDate = releaseDate,
        title = title,
        voteAverage = voteAverage,
        containerColor = null,
        onContainerColor = null
    )
}

fun MovieResponse.movieDb(movieList: String, position: Int): MovieDb {
    return MovieDb(
        movieList = movieList,
        dateAdded = System.currentTimeMillis(),
        page = null,
        position = position,
        movieId = id,
        overview = overview.orEmpty(),
        posterPath = posterPath.orEmpty(),
        backdropPath = backdropPath.orEmpty(),
        releaseDate = releaseDate,
        title = title,
        voteAverage = voteAverage,
        containerColor = null,
        onContainerColor = null
    )
}

internal val Movie.mapToMovieDb: MovieDb
    get() = MovieDb(
        movieList = "",
        dateAdded = 0L,
        page = null,
        position = 0,
        movieId = id,
        overview = overview.orEmpty(),
        posterPath = posterPath.orEmpty(),
        backdropPath = backdropPath.orEmpty(),
        releaseDate = releaseDate.orEmpty(),
        title = title.orEmpty(),
        voteAverage = voteAverage,
        containerColor = null,
        onContainerColor = null
    )