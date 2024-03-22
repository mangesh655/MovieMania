package com.mk.moviemania.navigation.feed

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.mk.moviemania.navigation.feed.ui.FeedRoute

fun NavGraphBuilder.feedGraph(
    navigateToDetails: (String, Int) -> Unit
) {
    composable(
        route = FeedDestination.route,
        arguments = listOf(
            navArgument("request_token") { type = NavType.StringType },
            navArgument("approved") { type = NavType.BoolType },
        ),
        deepLinks = listOf(
            navDeepLink { uriPattern = "movies://redirect_url?request_token={request_token}&approved={approved}" }
        )
    ) {
        FeedRoute(
            onNavigateToDetails = navigateToDetails
        )
    }
}