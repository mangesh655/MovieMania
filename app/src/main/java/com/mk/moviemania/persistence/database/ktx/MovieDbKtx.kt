package com.mk.moviemania.persistence.database.ktx

import com.mk.moviemania.network.TMDB_MOVIE_URL
import com.mk.moviemania.persistence.database.entity.MovieDb
import java.util.Locale

val MovieDb.isNotEmpty: Boolean
    get() = this != MovieDb.Empty

val MovieDb.url: String
    get() = String.format(Locale.US, TMDB_MOVIE_URL, movieId)

val MovieDb?.orEmpty: MovieDb
    get() = this ?: MovieDb.Empty