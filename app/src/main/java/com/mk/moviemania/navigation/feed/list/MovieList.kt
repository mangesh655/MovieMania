package com.mk.moviemania.navigation.feed.list

import com.mk.moviemania.common.SealedString
import com.mk.moviemania.navigation.feed.list.exceptions.InvalidMovieListException

sealed class MovieList(
    val name: String
): SealedString {

    data object NowPlaying: MovieList("now_playing")

    data object Popular: MovieList("popular")

    data object TopRated: MovieList("top_rated")

    data object Upcoming: MovieList("upcoming")

    companion object {
        val VALUES = listOf(
            NowPlaying,
            Popular,
            TopRated,
            Upcoming
        )

        fun transform(name: String): MovieList {
            return when (name) {
                NowPlaying.toString() -> NowPlaying
                Popular.toString() -> Popular
                TopRated.toString() -> TopRated
                Upcoming.toString() -> Upcoming
                else -> throw InvalidMovieListException
            }
        }
    }
}