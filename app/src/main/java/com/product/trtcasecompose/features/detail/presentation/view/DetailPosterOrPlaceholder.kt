package com.product.trtcasecompose.features.detail.presentation.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.product.trtcasecompose.R


@Composable
fun DetailPosterOrPlaceholder(imageUrl: String?, contentDescription: String?) {
    AsyncImage(
        model = imageUrl,
        contentDescription = contentDescription,
        modifier = Modifier.fillMaxSize(),
        placeholder = painterResource(R.drawable.placeholder_image),
        error = painterResource(R.drawable.placeholder_image),
        contentScale = ContentScale.Crop
    )
}