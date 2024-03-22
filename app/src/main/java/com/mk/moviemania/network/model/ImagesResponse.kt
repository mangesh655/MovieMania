package com.mk.moviemania.network.model

import android.media.Image
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImagesResponse(
    @SerializedName("id") val id: Int = 0,
    @SerializedName("backdrops") val backdrops: List<Image> = listOf(),
    @SerializedName("posters") val posters: List<Image> = listOf(),
    @SerializedName("logos") val logos: List<Image> = listOf()
)