package com.mk.moviemania.exceptions

internal data object InvalidLocaleException: Exception("Invalid locale") {
    private fun readResolve(): Any = InvalidLocaleException
}