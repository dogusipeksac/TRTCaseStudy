package com.product.trtcasecompose.core.common.connectivity.viewmodel

import androidx.lifecycle.viewModelScope
import com.product.trtcasecompose.core.common.base.viewmodel.BaseViewModel
import com.product.trtcasecompose.core.common.connectivity.ConnectivityObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConnectivityViewModel @Inject constructor(
    private val connectivityObserver: ConnectivityObserver
) : BaseViewModel() {

    private val _connectivityStatus = MutableStateFlow(ConnectivityObserver.Status.Unavailable)
    val connectivityStatus: StateFlow<ConnectivityObserver.Status> = _connectivityStatus

    private val _showReconnectMessage = MutableStateFlow(false)
    val showReconnectMessage: StateFlow<Boolean> = _showReconnectMessage

    private var previouslyConnected = true

    init {
        viewModelScope.launch {
            connectivityObserver.observe()
                .distinctUntilChanged()
                .collect { status ->
                    val isNowConnected = status == ConnectivityObserver.Status.Available
                    _connectivityStatus.value = status

                    if (isNowConnected && !previouslyConnected) {
                        _showReconnectMessage.value = true
                    }

                    previouslyConnected = isNowConnected
                }
        }
    }

    fun dismissReconnectMessage() {
        _showReconnectMessage.value = false
    }
}
