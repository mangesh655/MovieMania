package com.mk.moviemania.navigation.details.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.mk.moviemania.common.ui.ktx.isErrorOrEmpty
import com.mk.moviemania.common.ui.placeholder.PlaceholderHighlight
import com.mk.moviemania.common.ui.placeholder.material3.fade
import com.mk.moviemania.common.ui.placeholder.placeholder
import com.mk.moviemania.navigation.feed.accesibility.MoviesContentDescription
import com.mk.moviemania.network.formatBackdropImage
import com.mk.moviemania.persistence.database.entity.MovieDb
import com.mk.moviemania.persistence.database.ktx.isNotEmpty

@Composable
fun DetailsContent(
    movie: MovieDb,
    onGenerateColors: (Int, Palette) -> Unit,
    modifier: Modifier = Modifier,
    onContainerColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    placeholder: Boolean = false
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    var isNoImageVisible by remember { mutableStateOf(false) }

    if (!placeholder) {
        LaunchedEffect(key1 = movie.backdropPath.formatBackdropImage) {
            val imageRequest = ImageLoader(context).execute(ImageRequest.Builder(context)
                .data(movie.backdropPath.formatBackdropImage)
                .allowHardware(false)
                .build())
            if (imageRequest is SuccessResult) {
                val bitmap = imageRequest.drawable.toBitmap()
                Palette.from(bitmap).generate { palette ->
                    if (palette != null) {
                        onGenerateColors(movie.movieId, palette)
                    }
                }
            }
        }
    }

    ConstraintLayout(
        modifier = modifier.verticalScroll(scrollState)
    ) {
        val (image, title, overview) = createRefs()

        val imageRequest: ImageRequest? = if (placeholder) {
            null
        } else {
            ImageRequest.Builder(context)
                .data(movie.backdropPath.formatBackdropImage)
                .crossfade(true)
                .build()
        }

        AsyncImage(
            model = imageRequest,
            contentDescription = stringResource(MoviesContentDescription.MovieDetailsImage),
            modifier = Modifier
                .constrainAs(image) {
                    width = Dimension.fillToConstraints
                    height = Dimension.value(220.dp)
                    start.linkTo(parent.start, 16.dp)
                    top.linkTo(parent.top, 16.dp)
                    end.linkTo(parent.end, 16.dp)
                }
                .clip(MaterialTheme.shapes.small)
                .background(MaterialTheme.colorScheme.inversePrimary)
                .placeholder(
                    visible = placeholder,
                    color = MaterialTheme.colorScheme.inversePrimary,
                    shape = MaterialTheme.shapes.small,
                    highlight = PlaceholderHighlight.fade()
                )
                .clickable(
                    enabled = !placeholder && !isNoImageVisible,
                    onClick = { /*onNavigateToGallery(movie.movieId)*/ }
                ) ,
            onState = { state ->
                isNoImageVisible = movie.isNotEmpty && state.isErrorOrEmpty
            },
            contentScale = ContentScale.Crop
        )

        Text(
            text = movie.title,
            modifier = Modifier
                .constrainAs(title) {
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                    start.linkTo(parent.start, 16.dp)
                    top.linkTo(image.bottom, 8.dp)
                    end.linkTo(parent.end, 16.dp)
                }
                .placeholder(
                    visible = placeholder,
                    color = MaterialTheme.colorScheme.inversePrimary,
                    shape = MaterialTheme.shapes.small,
                    highlight = PlaceholderHighlight.fade()
                ),
            overflow = TextOverflow.Ellipsis,
            maxLines = 3,
            style = MaterialTheme.typography.titleLarge.copy(onContainerColor)
        )

        SelectionContainer(
            modifier = Modifier
                .constrainAs(overview) {
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                    start.linkTo(parent.start, 16.dp)
                    top.linkTo(title.bottom, 8.dp)
                    end.linkTo(parent.end, 16.dp)
                }
                .placeholder(
                    visible = placeholder,
                    color = MaterialTheme.colorScheme.inversePrimary,
                    shape = MaterialTheme.shapes.small,
                    highlight = PlaceholderHighlight.fade()
                )
        ) {
            Text(
                text = movie.overview,
                style = MaterialTheme.typography.bodyMedium.copy(onContainerColor)
            )
        }
    }
}