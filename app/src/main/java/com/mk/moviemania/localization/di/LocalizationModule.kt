package com.mk.moviemania.localization.di

import com.mk.moviemania.localization.LocaleController
import com.mk.moviemania.localization.LocaleControllerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface LocaleModule {

    @Binds
    @Singleton
    fun provideLocaleController(controller: LocaleControllerImpl): LocaleController
}