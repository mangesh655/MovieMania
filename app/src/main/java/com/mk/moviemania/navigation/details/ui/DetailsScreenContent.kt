package com.mk.moviemania.navigation.details.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.palette.graphics.Palette
import com.mk.moviemania.common.ui.ktx.displayCutoutWindowInsets
import com.mk.moviemania.common.ui.ktx.screenHeight
import com.mk.moviemania.common.ui.ktx.screenWidth
import com.mk.moviemania.navigation.details.ktx.isFailure
import com.mk.moviemania.navigation.details.ktx.movie
import com.mk.moviemania.navigation.details.ktx.movieUrl
import com.mk.moviemania.navigation.details.ktx.onPrimaryContainer
import com.mk.moviemania.navigation.details.ktx.primaryContainer
import com.mk.moviemania.navigation.details.ktx.scrolledContainerColor
import com.mk.moviemania.navigation.details.ktx.throwable
import com.mk.moviemania.navigation.details.ktx.toolbarTitle
import com.mk.moviemania.navigation.details.viewmodel.DetailsViewModel
import com.mk.moviemania.network.ktx.isAvailable
import com.mk.moviemania.network.model.ScreenState
import com.mk.networkinglib.internetConnectivity.NetworkStatus
import java.net.UnknownHostException

@JvmOverloads
@Composable
fun DetailsRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailsViewModel = hiltViewModel()
) {
    val detailsState by viewModel.detailsState.collectAsStateWithLifecycle()
    val networkStatus by viewModel.networkStatus.collectAsStateWithLifecycle()

    DetailsScreenContent(
        onBackClick = onBackClick,
        onGenerateColors = viewModel::onGenerateColors,
        detailsState = detailsState,
        networkStatus = networkStatus,
        onRetry = viewModel::retry,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun DetailsScreenContent(
    onBackClick: () -> Unit,
    onGenerateColors: (Int, Palette) -> Unit,
    detailsState: ScreenState,
    networkStatus: NetworkStatus,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    val lazyListState = rememberLazyListState()
    val topAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    if (networkStatus.isAvailable && detailsState.isFailure && detailsState.throwable is UnknownHostException) {
        onRetry()
    }

    val animateContainerColor = animateColorAsState(
        targetValue = detailsState.primaryContainer(),
        animationSpec = tween(
            durationMillis = 200,
            delayMillis = 0,
            easing = LinearEasing
        ),
        label = "animateContainerColor"
    )
    val animateOnContainerColor = animateColorAsState(
        targetValue = detailsState.onPrimaryContainer(),
        animationSpec = tween(
            durationMillis = 200,
            delayMillis = 0,
            easing = LinearEasing
        ),
        label = "animateOnContainerColor"
    )

    LazyRow(
        modifier = modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        state = lazyListState,
        flingBehavior = rememberSnapFlingBehavior(lazyListState = lazyListState)
    ) {
        item {
            Scaffold(
                modifier = Modifier
                    .width(screenWidth)
                    .height(screenHeight)
                    .nestedScroll(topAppBarScrollBehavior.nestedScrollConnection),
                topBar = {
                    DetailsToolbar(
                        movieTitle = detailsState.toolbarTitle,
                        movieUrl = detailsState.movieUrl,
                        onNavigationIconClick = onBackClick,
                        topAppBarScrollBehavior = topAppBarScrollBehavior,
                        onContainerColor = animateOnContainerColor.value,
                        scrolledContainerColor = detailsState.scrolledContainerColor(),
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                containerColor = animateContainerColor.value
            ) { innerPadding ->
                when (detailsState) {
                    is ScreenState.Loading -> {
                        DetailsLoading(
                            modifier = Modifier
                                .padding(innerPadding)
                                .windowInsetsPadding(displayCutoutWindowInsets)
                                .fillMaxSize()
                        )
                    }
                    is ScreenState.Content<*> -> {
                        DetailsContent(
                            modifier = Modifier
                                .padding(innerPadding)
                                .windowInsetsPadding(displayCutoutWindowInsets)
                                .fillMaxSize(),
                            movie = detailsState.movie,
                            onContainerColor = animateOnContainerColor.value,
                            onGenerateColors = onGenerateColors
                        )
                    }
                    is ScreenState.Failure -> {
                        DetailsFailure(
                            modifier = Modifier
                                .padding(innerPadding)
                                .windowInsetsPadding(displayCutoutWindowInsets)
                                .fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}