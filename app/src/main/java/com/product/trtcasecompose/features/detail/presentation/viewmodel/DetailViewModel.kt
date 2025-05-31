package com.product.trtcasecompose.features.detail.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.product.trtcasecompose.core.common.base.viewmodel.BaseViewModel
import com.product.trtcasecompose.core.common.state.UiState
import com.product.trtcasecompose.features.detail.domain.model.MovieDetail
import com.product.trtcasecompose.features.detail.domain.usecase.GetMovieDetailUseCase
import com.product.trtcasecompose.features.detail.domain.usecase.GetMovieTrailerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    private val getMovieTrailerUseCase: GetMovieTrailerUseCase,
) : BaseViewModel() {
    private val _detailState = MutableStateFlow<UiState<MovieDetail>>(UiState.Loading)
    val detailState: StateFlow<UiState<MovieDetail>> = _detailState

    fun loadMovieDetail(movieId: Int) {
        viewModelScope.launch {
            _detailState.value = UiState.Loading
            _detailState.value = getMovieDetailUseCase(movieId)
        }
    }


    private val _videoKey = MutableStateFlow<UiState<String?>>(
        UiState.Loading
    )
    val videoKey: StateFlow<UiState<String?>> = _videoKey

    fun loadMovieTrailer(movieId: Int) {
        viewModelScope.launch {
            val result = getMovieTrailerUseCase(movieId)
            _videoKey.value = result
        }
    }

}