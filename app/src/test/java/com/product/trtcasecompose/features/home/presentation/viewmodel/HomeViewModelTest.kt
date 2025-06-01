package com.product.trtcasecompose.features.home.presentation.viewmodel

import com.product.trtcasecompose.core.common.connectivity.ConnectivityObserver
import com.product.trtcasecompose.core.common.state.UiState
import com.product.trtcasecompose.features.home.domain.usecase.GetMoviesUseCase
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {
    private lateinit var viewModel: HomeViewModel
    private lateinit var getMoviesUseCase: GetMoviesUseCase
    private lateinit var connectivityObserver: ConnectivityObserver
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        getMoviesUseCase = mockk()
        connectivityObserver = mockk {
            coEvery { observe() } returns emptyFlow()
        }
    }

    @Test
    fun `fetchMovies should emit Success when use case returns data`() = runTest {

        val fake_movies = listOf(
            com.product.trtcasecompose.features.home.domain.model.Movie(
                id = 1,
                title = "Batman",
                overview = "A dark knight.",
                posterUrl = "batman.jpg"
            ),
            com.product.trtcasecompose.features.home.domain.model.Movie(
                id = 2,
                title = "Superman",
                overview = "A man of steel.",
                posterUrl = "superman.jpg"
            )
        )
        coEvery { getMoviesUseCase() } returns UiState.Success(fake_movies)

        viewModel = HomeViewModel(getMoviesUseCase)
        advanceUntilIdle()

        val state = viewModel.moviesState.value
        assertTrue(state is UiState.Success)
        assertEquals(fake_movies, (state as UiState.Success).data)
    }

    @Test
    fun `fetchMovies should emit Error when use case fails`() = runTest {
        coEvery { getMoviesUseCase() } returns UiState.Error("Server Error")

        viewModel = HomeViewModel(getMoviesUseCase)
        advanceUntilIdle()

        val state = viewModel.moviesState.value
        assertTrue(state is UiState.Error)
        assertEquals("Server Error", (state as UiState.Error).message)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
