package com.mk.moviemania.localization.model

import com.mk.moviemania.common.SealedString
import com.mk.moviemania.exceptions.InvalidLocaleException

sealed class AppLanguage(
    val code: String
): SealedString {

    data object English: AppLanguage("en")

    data object Russian: AppLanguage("ru")

    companion object {
        val VALUES = listOf(
            English,
            Russian
        )

        fun transform(code: String): AppLanguage {
            return when (code) {
                "en" -> English
                "ru" -> Russian
                else -> throw InvalidLocaleException
            }
        }
    }
}