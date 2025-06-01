package com.product.trtcasecompose.features.favorite.presentation.viewmodel

import app.cash.turbine.test
import com.product.trtcasecompose.core.common.state.UiState
import com.product.trtcasecompose.features.favorite.domain.model.FavoriteMovie
import com.product.trtcasecompose.features.favorite.domain.usecase.*
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

@OptIn(ExperimentalCoroutinesApi::class)
class FavoritesViewModelTest {

    private lateinit var getFavoritesUseCase: GetFavoritesUseCase
    private lateinit var addFavoriteUseCase: AddFavoriteUseCase
    private lateinit var removeFavoriteUseCase: RemoveFavoriteUseCase
    private lateinit var isFavoriteUseCase: IsFavoriteUseCase
    private lateinit var viewModel: FavoritesViewModel

    private val testDispatcher = StandardTestDispatcher()

    private val mockMovies = listOf(
        FavoriteMovie(id = 1, title = "Test Movie", posterUrl = "/poster.jpg", overview = "test"),
        FavoriteMovie(id = 2, title = "Another Movie", posterUrl = "/another.jpg", overview = "test1")
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        getFavoritesUseCase = mockk()
        addFavoriteUseCase = mockk(relaxed = true)
        removeFavoriteUseCase = mockk(relaxed = true)
        isFavoriteUseCase = mockk()

        every { getFavoritesUseCase.invoke() } returns flowOf(UiState.Success(mockMovies))

        viewModel = FavoritesViewModel(
            getFavoritesUseCase,
            addFavoriteUseCase,
            removeFavoriteUseCase,
            isFavoriteUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `addToFavorites calls use case`() = runTest(testDispatcher) {
        val movie = mockMovies[0]
        viewModel.addToFavorites(movie)
        testDispatcher.scheduler.advanceUntilIdle()
        coVerify { addFavoriteUseCase(movie) }
    }

    @Test
    fun `removeFromFavorites calls use case`() = runTest(testDispatcher) {
        val movie = mockMovies[1]
        viewModel.removeFromFavorites(movie)
        testDispatcher.scheduler.advanceUntilIdle()
        coVerify { removeFavoriteUseCase(movie) }
    }
}
