package com.mk.moviemania

import android.app.Application
import com.mk.moviemania.network.TMDB_API_ENDPOINT
import com.mk.networkinglib.NetworkingClient.INetworkSetup
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
internal class MovieApplication: Application() {

    @Inject
    lateinit var networkSetup: INetworkSetup

    override fun onCreate() {
        super.onCreate()
        networkSetup.setBaseUrl(TMDB_API_ENDPOINT)
        networkSetup.setApiKey(BuildConfig.TMDB_API_KEY)
    }
}