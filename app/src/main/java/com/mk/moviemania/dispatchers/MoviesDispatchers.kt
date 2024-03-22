package com.mk.moviemania.dispatchers

import kotlinx.coroutines.CoroutineDispatcher

interface MoviesDispatchers {
    val default: CoroutineDispatcher
    val io: CoroutineDispatcher
    val main: CoroutineDispatcher
    val immediate: CoroutineDispatcher
}