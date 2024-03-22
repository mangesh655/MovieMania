package com.mk.moviemania.network.ktx

import com.mk.networkinglib.internetConnectivity.NetworkStatus

val NetworkStatus.isAvailable: Boolean
    get() = this == NetworkStatus.Available