package com.mk.moviemania.theme

import android.graphics.Color
import android.os.Build
import androidx.activity.SystemBarStyle
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.mk.moviemania.common.theme.AppTheme
import com.mk.moviemania.common.theme.model.ComposeTheme
import com.mk.moviemania.common.ui.ktx.context

@Composable
fun MoviesTheme(
    theme: AppTheme = AppTheme.FollowSystem,
    dynamicColors: Boolean = false,
    enableEdgeToEdge: (SystemBarStyle, SystemBarStyle) -> Unit = { _, _ -> },
    content: @Composable () -> Unit
) {
    val darkTheme: Boolean = isSystemInDarkTheme()
    val detectDarkMode = darkTheme
    ComposeTheme(
        colorScheme = if (dynamicColors) {
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(
                context
            )
        } else {
            if (darkTheme) DarkColorScheme else LightColorScheme
        },
        detectDarkMode = darkTheme
    )

    enableEdgeToEdge(
        SystemBarStyle.auto(Color.TRANSPARENT, Color.TRANSPARENT) { detectDarkMode },
        SystemBarStyle.auto(Color.TRANSPARENT, Color.TRANSPARENT) { detectDarkMode }
    )

    MaterialTheme(
        colorScheme = colorScheme,
        shapes = MoviesShapes,
        typography = MoviesTypography
    ) {
        CompositionLocalProvider(LocalRippleTheme provides MoviesRippleTheme) {
            content()
        }
    }
}