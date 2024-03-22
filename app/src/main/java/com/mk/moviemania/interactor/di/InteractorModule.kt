package com.mk.moviemania.interactor.di

import com.mk.moviemania.interactor.MovieInteractor
import com.mk.moviemania.interactor.impl.MovieInteractorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface InteractorModule {

    @Binds
    @Singleton
    fun provideMovieInteractor(
        interactor: MovieInteractorImpl
    ): MovieInteractor
}