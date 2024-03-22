package com.mk.moviemania.navigation.details.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import com.mk.moviemania.common.ui.compose.iconbutton.BackIcon
import com.mk.moviemania.common.ui.compose.iconbutton.ShareIcon
import com.mk.moviemania.common.ui.ktx.displayCutoutWindowInsets
import com.mk.moviemania.navigation.feed.accesibility.MoviesContentDescription
import com.mk.moviemania.navigation.feed.accesibility.MoviesContentDescription.ShareIcon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsToolbar(
    movieTitle: String,
    movieUrl: String?,
    onNavigationIconClick: () -> Unit,
    topAppBarScrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier,
    onContainerColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    scrolledContainerColor: Color = MaterialTheme.colorScheme.inversePrimary,
) {
    TopAppBar(
        title = {
            Text(
                text = movieTitle,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleLarge.copy(onContainerColor)
            )
        },
        modifier = modifier,
        actions = {
            AnimatedVisibility(
                visible = movieUrl != null,
                modifier = Modifier.windowInsetsPadding(displayCutoutWindowInsets),
                enter = fadeIn()
            ) {
                if (movieUrl != null) {
                    ShareIcon(
                        url = movieUrl,
                        onContainerColor = onContainerColor
                    )
                }
            }
        },
        navigationIcon = {
            BackIcon(
                onClick = onNavigationIconClick,
                modifier = Modifier.windowInsetsPadding(displayCutoutWindowInsets),
                onContainerColor = onContainerColor
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            scrolledContainerColor = scrolledContainerColor
        ),
        scrollBehavior = topAppBarScrollBehavior
    )
}