package com.product.trtcasecompose.presentation.app.components


import android.widget.Toast
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.product.trtcasecompose.R
import com.product.trtcasecompose.core.common.connectivity.viewmodel.ConnectivityViewModel

@Composable
fun AppConnectivityListener() {
    val viewModel: ConnectivityViewModel = hiltViewModel()

    val status by viewModel.connectivityStatus.collectAsState()
    val showMessage by viewModel.showReconnectMessage.collectAsState()

    if (showMessage) {
        Toast.makeText(
            LocalContext.current,
            stringResource(R.string.reconnect_message),
            Toast.LENGTH_SHORT
        ).show()

        LaunchedEffect(Unit) {
            viewModel.dismissReconnectMessage()
        }
    }
}

