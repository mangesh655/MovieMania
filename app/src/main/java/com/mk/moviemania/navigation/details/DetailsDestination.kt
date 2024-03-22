package com.mk.moviemania.navigation.details

import com.mk.moviemania.navigation.feed.MoviesNavigationDestination

internal object DetailsDestination: MoviesNavigationDestination {

    override val route: String = "movie?movieList={movieList}&movieId={movieId}"

    override val destination: String = "movie"
}