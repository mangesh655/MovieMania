package com.mk.moviemania.common.ui.compose.iconbutton

import androidx.compose.foundation.Image
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import com.mk.moviemania.navigation.feed.accesibility.MoviesContentDescription
import com.mk.moviemania.resources.MoviesIcons

@Composable
fun BackIcon(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    onContainerColor: Color = MaterialTheme.colorScheme.onPrimaryContainer
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Image(
            imageVector = MoviesIcons.ArrowBack,
            contentDescription = stringResource(MoviesContentDescription.BackIcon),
            colorFilter = ColorFilter.tint(onContainerColor)
        )
    }
}