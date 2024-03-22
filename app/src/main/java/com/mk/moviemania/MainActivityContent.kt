package com.mk.moviemania

import androidx.activity.SystemBarStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.mk.moviemania.theme.MoviesTheme
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.mk.moviemania.navigation.feed.FeedDestination
import com.mk.moviemania.navigation.details.detailsGraph
import com.mk.moviemania.navigation.details.navigateToDetails
import com.mk.moviemania.navigation.feed.feedGraph

@Composable
internal fun MainActivityContent(
    enableEdgeToEdge: (SystemBarStyle, SystemBarStyle) -> Unit
) {
    val navHostController = rememberNavController()

    MoviesTheme(
        enableEdgeToEdge = enableEdgeToEdge
    ) {
        NavHost(
            navController = navHostController,
            startDestination = FeedDestination.route
        ) {

            feedGraph(
                navigateToDetails = navHostController::navigateToDetails
            )

            detailsGraph(
                navigateBack = navHostController::popBackStack,
            )
        }
    }
}
