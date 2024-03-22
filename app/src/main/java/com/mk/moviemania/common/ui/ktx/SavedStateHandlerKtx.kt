package com.mk.moviemania.common.ui.ktx

import androidx.lifecycle.SavedStateHandle

fun <T> SavedStateHandle.require(key: String): T = requireNotNull(this[key])