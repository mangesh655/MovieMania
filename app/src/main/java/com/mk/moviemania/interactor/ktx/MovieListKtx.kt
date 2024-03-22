package com.mk.moviemania.interactor.ktx

import com.mk.moviemania.navigation.feed.list.MovieList
import com.mk.moviemania.network.isTmdbApiKeyEmpty
import com.mk.moviemania.persistence.database.entity.MovieDb

val MovieList.nameOrLocalList: String
    get() = if (isTmdbApiKeyEmpty) MovieDb.MOVIES_LOCAL_LIST else name