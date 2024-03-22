package com.mk.moviemania.network.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName

data class Dates(
    @SerializedName("minimum") val minimumDate: String,
    @SerializedName("maximum") val maximumDate: String
)