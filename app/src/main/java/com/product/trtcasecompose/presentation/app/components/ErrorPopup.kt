package com.product.trtcasecompose.presentation.app.components

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.product.trtcasecompose.R

@Composable
fun ErrorPopup(
    message: String,
    onDismiss: () -> Unit,
    onRetry: () -> Unit={}
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = {
                onDismiss()
                onRetry()
            }) {
                Text(text = stringResource(R.string.retry_button))
            }
        },
        text = {
            Text(
                text = message,
                style = MaterialTheme.typography.body1
            )
        }
    )
}

