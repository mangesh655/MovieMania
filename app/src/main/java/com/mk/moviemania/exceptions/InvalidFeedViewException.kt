package com.mk.moviemania.exceptions

internal data object InvalidFeedViewException: Exception("Invalid feed view") {
    private fun readResolve(): Any = InvalidFeedViewException
}