package com.product.trtcasecompose.features.favorite.presentation.viewmodel

import com.product.trtcasecompose.core.common.connectivity.ConnectivityObserver
import com.product.trtcasecompose.core.common.state.UiState
import com.product.trtcasecompose.core.util.TestConstants.ERROR_MESSAGE
import com.product.trtcasecompose.core.util.TestConstants.FAKE_MOVIES
import com.product.trtcasecompose.features.home.domain.usecase.GetMoviesUseCase
import com.product.trtcasecompose.features.home.presentation.viewmodel.HomeViewModel
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
        coEvery { getMoviesUseCase() } returns UiState.Success(FAKE_MOVIES)

        viewModel = HomeViewModel(getMoviesUseCase, connectivityObserver)
        advanceUntilIdle()

        val state = viewModel.moviesState.value
        assertTrue(state is UiState.Success)
        assertEquals(FAKE_MOVIES, (state as UiState.Success).data)
    }

    @Test
    fun `fetchMovies should emit Error when use case fails`() = runTest {
        coEvery { getMoviesUseCase() } returns UiState.Error(ERROR_MESSAGE)

        viewModel = HomeViewModel(getMoviesUseCase, connectivityObserver)
        advanceUntilIdle()

        val state = viewModel.moviesState.value
        assertTrue(state is UiState.Error)
        assertEquals(ERROR_MESSAGE, (state as UiState.Error).message)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
