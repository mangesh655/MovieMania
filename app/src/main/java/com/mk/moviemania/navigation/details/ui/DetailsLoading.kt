package com.mk.moviemania.navigation.details.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mk.moviemania.persistence.database.entity.MovieDb

@Composable
fun DetailsLoading(
    modifier: Modifier = Modifier
) {
    DetailsContent(
        modifier = modifier,
        movie = MovieDb.Empty,
        onGenerateColors = { _,_ -> },
        placeholder = true
    )
}