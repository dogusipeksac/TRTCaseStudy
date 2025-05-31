package com.product.trtcasecompose.features.detail.domain.usecase


import com.product.trtcasecompose.core.common.state.UiState
import com.product.trtcasecompose.features.detail.domain.repository.MovieDetailRepository
import com.product.trtcasecompose.features.detail.domain.util.MovieVideoConstants
import javax.inject.Inject

class GetMovieTrailerUseCase @Inject constructor(
    private val repository: MovieDetailRepository
) {
    // Film ID ile ilgili fragman veya teaser videosunun YouTube key'ini döner
    suspend operator fun invoke(movieId: Int): UiState<String?> {
        return when (val result = repository.getMovieVideo(movieId)) {

            // Veri başarıyla geldiyse, öncelikle Trailer, yoksa Teaser/Featurette döndür
            is UiState.Success -> {
                val videos = result.data

                // Öncelik: YouTube'da yayınlanmış Trailer tipi video
                val trailer = videos.firstOrNull {
                    it.site.equals(MovieVideoConstants.SITE_YOUTUBE, ignoreCase = true) &&
                            it.type.equals(MovieVideoConstants.TYPE_TRAILER, ignoreCase = true)
                }

                // Yedek: Teaser ya da Featurette tipi video
                val fallback = videos.firstOrNull {
                    it.site.equals(MovieVideoConstants.SITE_YOUTUBE, ignoreCase = true) &&
                            (it.type.equals(MovieVideoConstants.TYPE_TEASER, ignoreCase = true) ||
                                    it.type.equals(MovieVideoConstants.TYPE_FEATURETTE, ignoreCase = true))
                }

                // Trailer varsa onu, yoksa fallback'i döndür
                UiState.Success(trailer?.key ?: fallback?.key)
            }

            // Hata durumunu ileri taşı
            is UiState.Error -> UiState.Error(result.message)

            // Yükleme durumu
            is UiState.Loading -> UiState.Loading
        }
    }
}
