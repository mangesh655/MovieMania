package com.mk.moviemania.navigation.feed.list.exceptions

internal data object InvalidMovieListException: Exception("Invalid movie list") {
    private fun readResolve(): Any = InvalidMovieListException
}