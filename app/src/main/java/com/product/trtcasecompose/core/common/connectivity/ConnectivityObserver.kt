package com.product.trtcasecompose.core.common.connectivity

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {
    fun observe(): Flow<Status>
    enum class Status {
        Available,
        Unavailable,
        Losing,
        Lost
    }
}