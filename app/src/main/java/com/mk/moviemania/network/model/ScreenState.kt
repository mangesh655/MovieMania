package com.mk.moviemania.network.model

sealed interface ScreenState {

    data object Loading: ScreenState

    data class Content<T>(val data: T): ScreenState

    data class Failure(val throwable: Throwable): ScreenState
}