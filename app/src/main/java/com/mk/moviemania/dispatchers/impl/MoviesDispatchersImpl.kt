package com.mk.moviemania.dispatchers.impl

import com.mk.moviemania.dispatchers.MoviesDispatchers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

internal class MoviesDispatchersImpl @Inject constructor(): MoviesDispatchers {

    override val default: CoroutineDispatcher
        get() = Dispatchers.Default

    override val io: CoroutineDispatcher
        get() = Dispatchers.IO

    override val main: CoroutineDispatcher
        get() = Dispatchers.Main

    override val immediate: CoroutineDispatcher
        get() = Dispatchers.Main.immediate
}