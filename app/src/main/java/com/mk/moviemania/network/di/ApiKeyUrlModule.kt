package com.mk.moviemania.network.di

import com.mk.moviemania.BuildConfig
import com.mk.networkinglib.Interceptor.ApikeyInterceptor
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.hilt.migration.DisableInstallInCheck
import javax.inject.Singleton

@Component(modules = [ApikeyUrlInterceptorModule::class])
@Singleton
interface ApikeyComponent {
    fun provideApikeyInterceptor(): ApikeyInterceptor

    fun provideBaseurl() : String
}

@Module
@DisableInstallInCheck
object ApikeyUrlInterceptorModule {

    @Provides
    @Singleton
    fun provideApikeyInterceptor(): ApikeyInterceptor {
        return ApikeyInterceptor(BuildConfig.TMDB_API_KEY)
    }

    @Provides
    @Singleton
    fun provideBaseurl(): String {
        return BuildConfig.TMDB_API_KEY
    }
}