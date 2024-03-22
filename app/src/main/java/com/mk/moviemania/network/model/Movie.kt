package com.mk.moviemania.network.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.intellij.lang.annotations.Language


@Serializable
data class Movie(
    @SerializedName("id") val id: Int = 0,
    @SerializedName("imdb_id") val imdbId: String? = null,
    @SerializedName("adult") val adult: Boolean? = false,
    @SerializedName("backdrop_path") val backdropPath: String? = null,
    //@SerializedName("belongs_to_collection") val belongsToCollection: Collection?,
    @SerializedName("budget") val budget: Int = 0,
    @SerializedName("genres") val genres: List<Genre> = listOf(),
    @SerializedName("homepage") val homepage: String? = null,
    @SerializedName("original_language") val originalLanguage: String = "en",
    @SerializedName("original_title") val originalTitle: String = "",
    @SerializedName("overview") val overview: String? = null,
    @SerializedName("popularity") val popularity: Double = 0.0,
    @SerializedName("poster_path") val posterPath: String? = null,
    //@SerializedName("production_companies") val companies: List<Company>,
    @SerializedName("production_countries") val countries: List<Country> = listOf(),
    @SerializedName("release_date") val releaseDate: String? = null,
    @SerializedName("revenue") val revenue: Long = 0,
    @SerializedName("runtime") val runtime: Int = 0,
    @SerializedName("spoken_languages") val languages: List<Language> = listOf(),
    @SerializedName("status") val status: String = "",
    @SerializedName("tagline") val tagline: String = "",
    @SerializedName("title") val title: String? = null,
    @SerializedName("video") val video: Boolean = false,
    @SerializedName("vote_average") val voteAverage: Float = 0.0f,
    @SerializedName("vote_count") val voteCount: Int = 0,
    //@SerializedName("media_type") val mediaType: String,
    //@SerializedName("genre_ids") val genreIds: List<Int>,
    //@SerializedName("credits") val credits: CreditsResponse?
) {

    companion object {
        const val TV = "tv"
        const val MOVIE = "movie"
        const val CREDITS = "credits"

        const val ASC = "created_at.asc"
        const val DESC = "created_at.desc"

        const val NOW_PLAYING = "now_playing"
        const val POPULAR = "popular"
        const val TOP_RATED = "top_rated"
        const val UPCOMING = "upcoming"

        const val SIMILAR = "similar"
        const val RECOMMENDATIONS = "recommendations"

        const val FAVORITE = "favorite"
        const val WATCHLIST = "watchlist"
    }
}