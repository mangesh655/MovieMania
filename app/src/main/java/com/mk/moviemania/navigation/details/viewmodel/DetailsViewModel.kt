package com.mk.moviemania.navigation.details.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.palette.graphics.Palette
import com.mk.moviemania.common.BaseViewModel
import com.mk.moviemania.common.ui.ktx.require
import com.mk.moviemania.interactor.Interactor
import com.mk.moviemania.network.model.ScreenState
import com.mk.networkinglib.internetConnectivity.NetworkManager
import com.mk.networkinglib.internetConnectivity.NetworkStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    networkManager: NetworkManager,
    private val interactor: Interactor
): BaseViewModel() {

    private val movieList: String? = savedStateHandle["movieList"]
    private val movieId: Int = savedStateHandle.require("movieId")

    private val _detailsState: MutableStateFlow<ScreenState> = MutableStateFlow(ScreenState.Loading)
    val detailsState: StateFlow<ScreenState> = _detailsState.asStateFlow()

    val networkStatus: StateFlow<NetworkStatus> = networkManager.status
        .stateIn(
            scope = this,
            started = SharingStarted.Lazily,
            initialValue = NetworkStatus.Unavailable
        )

    init {
        loadMovie()
    }

    fun retry() = loadMovie()

    fun onGenerateColors(movieId: Int, palette: Palette) = launch {
        val containerColor = palette.vibrantSwatch?.rgb
        val onContainerColor = palette.vibrantSwatch?.bodyTextColor
        if (containerColor != null && onContainerColor != null) {
            interactor.updateMovieColors(movieId, containerColor, onContainerColor)
            if (movieList != null) {
                _detailsState.value = ScreenState.Content(interactor.movie(movieList, movieId))
            }
        }
    }

    private fun loadMovie() = launch {
        val movieDb = interactor.movieDetails(movieList.orEmpty(), movieId)
        _detailsState.value = ScreenState.Content(movieDb)
    }
}