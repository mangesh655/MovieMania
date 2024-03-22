package com.mk.moviemania.net

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mk.moviemania.network.model.ImagesResponse
import com.mk.moviemania.network.model.Movie
import com.mk.moviemania.network.model.MovieResponse
import com.mk.moviemania.network.movieservice.MovieNetworkService
import com.mk.networkinglib.NetworkingClient.INetworkCall
import com.mk.networkinglib.util.Result
import javax.inject.Inject

class MovieNetworkServiceImpl @Inject constructor(
    private val networkCall: INetworkCall,
    private val gson: Gson
) : MovieNetworkService {

    override suspend fun movies(
        list: String,
        language: String,
        page: Int
    ): com.mk.moviemania.network.model.Result<MovieResponse> {
        var response = networkCall.getData<com.mk.moviemania.network.model.Result<MovieResponse>>(
            "movie/$list",
            mutableMapOf("language" to language, "page" to page)
        )

        when (response) {
            is Result.Error -> com.mk.moviemania.network.model.Result<MovieResponse>()
            is Result.Success -> {
                if (response.data != null) {
                    return gson.fromJson<com.mk.moviemania.network.model.Result<MovieResponse>>(
                        gson.toJson(response.data),
                        object : TypeToken<com.mk.moviemania.network.model.Result<MovieResponse>>() {}.type
                    )
                }
            }
        }

        return com.mk.moviemania.network.model.Result<MovieResponse>()
    }

    override suspend fun movie(
        movieId: Int,
        language: String
    ): Movie {

        var response = networkCall.getData<Movie>(
            "movie/$movieId",
            mutableMapOf("language" to language)
        )

        when (response) {
            is Result.Error -> Movie()
            is Result.Success -> {
                if (response.data != null) {
                    return response.data!!
                }
            }
        }

        return Movie()
    }

    override suspend fun images(movieId: Int): ImagesResponse {

        var response = networkCall.getData<ImagesResponse>(
            "movie/$movieId/images",
            mutableMapOf()
        )

        when (response) {
            is Result.Error -> ImagesResponse()
            is Result.Success -> {
                if (response.data != null) {
                    return response.data!!
                }
            }
        }

        return ImagesResponse()
    }

}