package com.mk.moviemania.exceptions

import com.mk.moviemania.exceptions.ApiKeyNotNullException
import com.mk.moviemania.network.isTmdbApiKeyEmpty

internal fun checkApiKeyNotNullException() {
    if (isTmdbApiKeyEmpty) throw ApiKeyNotNullException
}