package com.mk.moviemania.network.movieservice

import com.mk.moviemania.network.model.ImagesResponse
import com.mk.moviemania.network.model.Movie
import com.mk.moviemania.network.model.MovieResponse
import com.mk.moviemania.network.model.Result

interface MovieNetworkService {

    suspend fun movies(list: String, language: String, page: Int) : Result<MovieResponse>

    suspend fun movie(movieId: Int, language: String): Movie

    suspend fun images(movieId: Int): ImagesResponse
}