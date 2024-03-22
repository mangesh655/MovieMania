package com.mk.moviemania.network.ktx

val <T> com.mk.moviemania.network.model.Result<T>.nextPage: Int
    get() = page.plus(1)

val <T> com.mk.moviemania.network.model.Result<T>.isPaginationReached: Boolean
    get() = page == totalPages

val <T> com.mk.moviemania.network.model.Result<T>.isEmpty: Boolean
    get() = page == 1 && results.isEmpty()
