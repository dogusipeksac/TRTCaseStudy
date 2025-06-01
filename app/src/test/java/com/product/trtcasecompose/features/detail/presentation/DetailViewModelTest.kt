package com.product.trtcasecompose.features.detail.presentation

import com.product.trtcasecompose.features.detail.presentation.viewmodel.DetailViewModel

import app.cash.turbine.test
import com.product.trtcasecompose.core.common.state.UiState
import com.product.trtcasecompose.features.detail.domain.model.MovieDetail
import com.product.trtcasecompose.features.detail.domain.usecase.GetMovieDetailUseCase
import com.product.trtcasecompose.features.detail.domain.usecase.GetMovieTrailerUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DetailViewModelTest {

    private lateinit var getMovieDetailUseCase: GetMovieDetailUseCase
    private lateinit var getMovieTrailerUseCase: GetMovieTrailerUseCase
    private lateinit var viewModel: DetailViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher) // 🛠 Dispatchers.Main simülasyonu

        getMovieDetailUseCase = mockk()
        getMovieTrailerUseCase = mockk()
        viewModel = DetailViewModel(getMovieDetailUseCase, getMovieTrailerUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // 🔄 eski haline döndür
    }

    @Test
    fun `loadMovieDetail emits loading then success`() = runTest(testDispatcher) {
        val movieId = 1
        val fakeDetail = MovieDetail(
            id = 1,
            title = "Fake Movie",
            overview = "Overview",
            posterUrl = "/poster.jpg",
        )

        coEvery { getMovieDetailUseCase(movieId) } returns UiState.Success(fakeDetail)

        viewModel.detailState.test {
            viewModel.loadMovieDetail(movieId)
            testDispatcher.scheduler.advanceUntilIdle()

            assert(awaitItem() is UiState.Loading)
            assert(awaitItem() == UiState.Success(fakeDetail))
        }
    }

    @Test
    fun `loadMovieDetail emits loading then error`() = runTest(testDispatcher) {
        val movieId = 1
        val errorMessage = "Something went wrong"

        coEvery { getMovieDetailUseCase(movieId) } returns UiState.Error(errorMessage)

        viewModel.detailState.test {
            viewModel.loadMovieDetail(movieId)
            testDispatcher.scheduler.advanceUntilIdle()

            assert(awaitItem() is UiState.Loading)
            assert(awaitItem() == UiState.Error(errorMessage))
        }
    }

    @Test
    fun `loadMovieTrailer emits error`() = runTest(testDispatcher) {
        val movieId = 1
        val errorMessage = "Trailer not found"

        coEvery { getMovieTrailerUseCase(movieId) } returns UiState.Error(errorMessage)

        viewModel.videoKey.test {
            viewModel.loadMovieTrailer(movieId)
            testDispatcher.scheduler.advanceUntilIdle()

            assert(awaitItem() is UiState.Loading)
            assert(awaitItem() == UiState.Error(errorMessage))
        }
    }

}
