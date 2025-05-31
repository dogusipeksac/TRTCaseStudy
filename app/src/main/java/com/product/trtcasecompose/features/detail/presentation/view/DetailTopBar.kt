package com.product.trtcasecompose.features.detail.presentation.view
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.product.trtcasecompose.R
import com.product.trtcasecompose.core.common.state.UiState

@Composable
fun DetailTopBar(
    title: String,
    isFavoriteState: UiState<Boolean>,
    onBack: () -> Unit,
    onToggleFavorite: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                title,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.back_button_content_desc)
                )
            }
        },
        actions = {
            when (isFavoriteState) {
                is UiState.Success -> {
                    val isFav = isFavoriteState.data
                    IconButton(onClick = onToggleFavorite) {
                        Icon(
                            imageVector = if (isFav) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = stringResource(R.string.favorite_button_content_desc),
                        )
                    }
                }

                is UiState.Loading -> CircularProgressIndicator(modifier = Modifier.size(24.dp))

                is UiState.Error -> Icon(
                    Icons.Default.Warning,
                    contentDescription = stringResource(R.string.favorite_error_icon_desc)
                )
            }
        }
    )
}
