package com.mk.moviemania.repository.di

import com.mk.moviemania.repository.MovieRepository
import com.mk.moviemania.repository.MovieRepositoryImpl
import com.mk.moviemania.repository.PagingKeyRepository
import com.mk.moviemania.repository.PagingKeyRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface RepositoryModule {

    @Binds
    @Singleton
    fun provideMovieRepository(
        repository: MovieRepositoryImpl
    ): MovieRepository

    @Binds
    @Singleton
    fun providePagingKeyRepository(
        repository: PagingKeyRepositoryImpl
    ): PagingKeyRepository
}