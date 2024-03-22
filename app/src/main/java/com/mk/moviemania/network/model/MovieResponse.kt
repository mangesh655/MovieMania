package com.mk.moviemania.network.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieResponse(
    @SerializedName("id") val id: Int = 0,
    @SerializedName("overview") val overview: String?= null,
    @SerializedName("poster_path") val posterPath: String? = null,
    @SerializedName("backdrop_path") val backdropPath: String? = null,
    @SerializedName("release_date") val releaseDate: String = "",
    @SerializedName("title") val title: String = "",
    @SerializedName("vote_average") val voteAverage: Float = 0.0f,
    @SerializedName("genre_ids") val genreIds: List<Int> = listOf()
) {
    companion object {
        const val DEFAULT_PAGE_SIZE = 20
    }
}