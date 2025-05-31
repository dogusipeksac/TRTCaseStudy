package com.product.trtcasecompose.features.home.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.product.trtcasecompose.core.common.base.viewmodel.BaseViewModel
import com.product.trtcasecompose.core.common.state.UiState
import com.product.trtcasecompose.core.common.connectivity.ConnectivityObserver
import com.product.trtcasecompose.features.home.domain.model.Movie
import com.product.trtcasecompose.features.home.domain.usecase.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
) : BaseViewModel() {

    private val _moviesState = MutableStateFlow<UiState<List<Movie>>>(UiState.Loading)
    val moviesState: StateFlow<UiState<List<Movie>>> = _moviesState


    init {
        fetchMovies()
    }

    fun fetchMovies() {
        viewModelScope.launch {
            _moviesState.value = UiState.Loading
            _moviesState.value = getMoviesUseCase() // doğrudan UiState döner
        }
    }


}
