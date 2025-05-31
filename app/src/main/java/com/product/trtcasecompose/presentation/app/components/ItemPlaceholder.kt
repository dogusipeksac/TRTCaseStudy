package com.product.trtcasecompose.presentation.app.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.product.trtcasecompose.R

@Composable
fun ItemPosterPlaceholder(
    imageUrl: String,
    contentDescription: String,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = imageUrl,
        contentDescription = contentDescription,
        modifier = modifier,
        placeholder = painterResource(R.drawable.placeholder_image),
        error = painterResource(R.drawable.placeholder_image),
        contentScale = ContentScale.Crop
    )
}