package com.mk.moviemania.network.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
open class Result<T> {
    @SerializedName("id")
    val id: Int = 0
    @SerializedName("page")
    val page: Int = 0
    @SerializedName("results")
    val results: List<T> = emptyList()
    @SerializedName("total_pages")
    val totalPages: Int = 0
    @SerializedName("total_results")
    val totalResults: Int = 0
    @SerializedName("dates")
    val dates: Dates? = null
}