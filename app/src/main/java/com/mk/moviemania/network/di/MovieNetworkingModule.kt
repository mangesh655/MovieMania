package com.mk.moviemania.network.di

import com.mk.moviemania.net.MovieNetworkServiceImpl
import com.mk.moviemania.network.movieservice.MovieNetworkService
import com.mk.networkinglib.NetworkingClient.NetworkApiService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface MovieNetworkingModule {

    @Binds
    @Singleton
    fun provideMovieNetworkingServiceRepository(
        repository: MovieNetworkServiceImpl
    ): MovieNetworkService

}