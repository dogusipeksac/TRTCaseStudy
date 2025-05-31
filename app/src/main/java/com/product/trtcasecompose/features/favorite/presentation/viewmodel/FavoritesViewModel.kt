package com.product.trtcasecompose.features.favorite.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.product.trtcasecompose.core.common.base.viewmodel.BaseViewModel
import com.product.trtcasecompose.core.common.state.UiState
import com.product.trtcasecompose.core.utils.ErrorMessages
import com.product.trtcasecompose.features.favorite.domain.model.FavoriteMovie
import com.product.trtcasecompose.features.favorite.domain.usecase.AddFavoriteUseCase
import com.product.trtcasecompose.features.favorite.domain.usecase.GetFavoritesUseCase
import com.product.trtcasecompose.features.favorite.domain.usecase.IsFavoriteUseCase
import com.product.trtcasecompose.features.favorite.domain.usecase.RemoveFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val removeFavoriteUseCase: RemoveFavoriteUseCase,
    private val isFavoriteUseCase: IsFavoriteUseCase,
) : BaseViewModel() {

    val favoriteMovies: StateFlow<UiState<List<FavoriteMovie>>> =
        getFavoritesUseCase()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UiState.Loading)

    // Favori ID'leri (tek tek film favori mi diye kontrol etmek yerine kullanılır)
    val favoriteIds: StateFlow<Set<Int>> = favoriteMovies
        .map { state ->
            when (state) {
                is UiState.Success -> state.data.map { it.id }.toSet()
                else -> emptySet()
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptySet())

    // Artık bu yapı sadece DetailScreen için kullanılabilir
    private val _isFavorite = MutableStateFlow<UiState<Boolean>>(UiState.Loading)
    val isFavorite: StateFlow<UiState<Boolean>> get() = _isFavorite

    fun checkIsFavorite(movieId: Int) {
        viewModelScope.launch {
            isFavoriteUseCase(movieId)
                .catch { e ->
                    _isFavorite.value = UiState.Error(
                        e.message ?: ErrorMessages.UNKNOWN
                    )
                }
                .collect { result ->
                    _isFavorite.value = result
                }
        }
    }

    fun addToFavorites(movie: FavoriteMovie) {
        viewModelScope.launch {
            addFavoriteUseCase(movie)
        }
    }

    fun removeFromFavorites(movie: FavoriteMovie) {
        viewModelScope.launch {
            removeFavoriteUseCase(movie)
        }
    }


}


