package com.mk.moviemania.dispatchers.di

import com.mk.moviemania.dispatchers.MoviesDispatchers
import com.mk.moviemania.dispatchers.impl.MoviesDispatchersImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface DispatchersModule {

    @Binds
    fun provideDispatchers(
        dispatchers: MoviesDispatchersImpl
    ): MoviesDispatchers
}