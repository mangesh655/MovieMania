package com.mk.moviemania.common.ui.compose.iconbutton

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.mk.moviemania.R
import com.mk.moviemania.navigation.feed.accesibility.MoviesContentDescription
import com.mk.moviemania.resources.MoviesIcons

@Composable
fun ShareIcon(
    url: String,
    modifier: Modifier = Modifier,
    onContainerColor: Color = MaterialTheme.colorScheme.onPrimaryContainer
) {
    val context = LocalContext.current
    val resultContract = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {}

    val onShareUrl: () -> Unit = {
        Intent().apply {
            type = "text/plain"
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, url)
        }.also { intent: Intent ->
            resultContract.launch(Intent.createChooser(intent, context.getString(R.string.share_via)))
        }
    }

    IconButton(
        onClick = onShareUrl,
        modifier = modifier
    ) {
        Image(
            imageVector = MoviesIcons.Share,
            contentDescription = stringResource(MoviesContentDescription.ShareIcon),
            colorFilter = ColorFilter.tint(onContainerColor)
        )
    }
}