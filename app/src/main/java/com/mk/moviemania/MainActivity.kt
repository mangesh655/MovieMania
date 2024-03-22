package com.mk.moviemania

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class MainActivity: FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        //installSplashScreen().apply { setKeepOnScreenCondition { viewModel.splashLoading.value } }
        super.onCreate(savedInstanceState)
        setContent {
            MainActivityContent { statusBarStyle, navigationBarStyle ->
                enableEdgeToEdge(statusBarStyle, navigationBarStyle)
            }
        }
    }
}