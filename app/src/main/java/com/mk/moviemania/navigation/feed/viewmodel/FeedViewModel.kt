package com.mk.moviemania.navigation.feed.viewmodel

import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mk.moviemania.common.BaseViewModel
import com.mk.moviemania.interactor.Interactor
import com.mk.moviemania.navigation.feed.list.MovieList
import com.mk.moviemania.navigation.feed.ui.FeedView
import com.mk.moviemania.persistence.database.entity.MovieDb
import com.mk.networkinglib.internetConnectivity.NetworkManager
import com.mk.networkinglib.internetConnectivity.NetworkStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val interactor: Interactor,
    networkManager: NetworkManager
): BaseViewModel() {

    val networkStatus: StateFlow<NetworkStatus> = networkManager.status
        .stateIn(
            scope = this,
            started = SharingStarted.Lazily,
            initialValue = NetworkStatus.Unavailable
        )

    val currentFeedView: StateFlow<FeedView> = interactor.currentFeedView
        .stateIn(
            scope = this,
            started = SharingStarted.Lazily,
            initialValue = runBlocking { interactor.currentFeedView.first() }
        )

    val currentMovieList: StateFlow<MovieList> = interactor.currentMovieList
        .stateIn(
            scope = this,
            started = SharingStarted.Lazily,
            initialValue = runBlocking { interactor.currentMovieList.first() }
        )

    val pagingDataFlow: Flow<PagingData<MovieDb>> = currentMovieList
        .flatMapLatest { movieList -> interactor.moviesPagingData(movieList) }
        .cachedIn(this)

    fun selectMovieList(movieList: MovieList) = launch {
        interactor.selectMovieList(movieList)
    }
}