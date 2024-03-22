package com.mk.moviemania.navigation.feed.ktx

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.mk.moviemania.R
import com.mk.moviemania.navigation.feed.list.MovieList

internal val MovieList.titleText: String
    @Composable get() = when (this) {
        is MovieList.NowPlaying -> stringResource(R.string.feed_title_now_playing)
        is MovieList.Popular -> stringResource(R.string.feed_title_popular)
        is MovieList.TopRated -> stringResource(R.string.feed_title_top_rated)
        is MovieList.Upcoming -> stringResource(R.string.feed_title_upcoming)
    }