package com.mk.moviemania.localization

import com.mk.moviemania.localization.model.AppLanguage
import kotlinx.coroutines.flow.Flow


interface LocaleController {

    val language: String

    val appLanguage: Flow<AppLanguage>

    suspend fun selectLanguage(language: AppLanguage)
}