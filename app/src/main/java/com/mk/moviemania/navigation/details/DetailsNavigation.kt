package com.mk.moviemania.navigation.details

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.mk.moviemania.navigation.details.ui.DetailsRoute

fun NavController.navigateToDetails(movieList: String, movieId: Int) {
    navigate("movie?movieList=$movieList&movieId=$movieId")
}

fun NavGraphBuilder.detailsGraph(
    navigateBack: () -> Unit,
) {
    composable(
        route = DetailsDestination.route,
        arguments = listOf(
            navArgument("movieList") {
                type = NavType.StringType
                nullable = true
            },
            navArgument("movieId") { type = NavType.LongType },
        ),
        deepLinks = listOf(
            navDeepLink { uriPattern = "https://www.themoviedb.org/movie/{movieId}" },
            navDeepLink { uriPattern = "movies://details/{movieId}" }
        )
    ) {
        DetailsRoute(
            onBackClick = navigateBack
        )
    }
}