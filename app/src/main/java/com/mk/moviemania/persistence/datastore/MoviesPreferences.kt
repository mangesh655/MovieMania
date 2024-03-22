package com.mk.moviemania.persistence.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MoviesPreferences @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    val feedViewFlow: Flow<String?>
        get() = dataStore.data.map { preferences -> preferences[PREFERENCE_FEED_VIEW_KEY] }

    val movieListFlow: Flow<String?>
        get() = dataStore.data.map { preferences -> preferences[PREFERENCE_MOVIE_LIST_KEY] }

    suspend fun setMovieList(movieList: String) {
        dataStore.edit { preferences ->
            preferences[PREFERENCE_MOVIE_LIST_KEY] = movieList
        }
    }

    private companion object {
        private val PREFERENCE_FEED_VIEW_KEY = stringPreferencesKey("feed_view")
        private val PREFERENCE_MOVIE_LIST_KEY = stringPreferencesKey("movie_list")
    }
}