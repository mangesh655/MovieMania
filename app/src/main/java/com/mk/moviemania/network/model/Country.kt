package com.mk.moviemania.network.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Country(
    @SerializedName("iso_3166_1") val country: String,
    @SerializedName("name") val name: String
)