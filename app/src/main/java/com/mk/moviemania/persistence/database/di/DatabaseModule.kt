package com.mk.moviemania.persistence.database.di

import android.content.Context
import com.mk.moviemania.persistence.AppDatabase
import com.mk.moviemania.persistence.database.dao.MovieDao
import com.mk.moviemania.persistence.database.dao.PagingKeyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase = AppDatabase.getInstance(context)

    @Provides
    fun provideMovieDao(appDatabase: AppDatabase): MovieDao = appDatabase.movieDao()

    @Provides
    fun providePagingKeyDao(appDatabase: AppDatabase): PagingKeyDao = appDatabase.pagingKeyDao()

}