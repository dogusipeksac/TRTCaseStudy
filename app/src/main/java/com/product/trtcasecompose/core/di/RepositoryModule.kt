package com.product.trtcasecompose.core.di

import com.product.trtcasecompose.core.common.connectivity.util.NetworkChecker
import com.product.trtcasecompose.features.detail.data.remote.api.MovieDetailApiService
import com.product.trtcasecompose.features.detail.data.repository.MovieDetailRepositoryImpl
import com.product.trtcasecompose.features.detail.domain.repository.MovieDetailRepository
import com.product.trtcasecompose.features.favorite.data.local.dao.FavoriteMovieDao
import com.product.trtcasecompose.features.home.data.remote.api.MovieApiService
import com.product.trtcasecompose.features.favorite.data.repository.FavoriteRepositoryImpl
import com.product.trtcasecompose.features.home.data.repository.MovieRepositoryImpl
import com.product.trtcasecompose.features.favorite.domain.repository.FavoriteRepository
import com.product.trtcasecompose.features.home.data.local.dao.MovieDao
import com.product.trtcasecompose.features.home.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun provideMovieRepository(
        api: MovieApiService,
        dao: MovieDao,
        networkChecker: NetworkChecker
    ): MovieRepository {
        return MovieRepositoryImpl(api, dao, networkChecker)
    }

    @Provides
    @Singleton
    fun provideFavoriteRepository(
        dao: FavoriteMovieDao
    ): FavoriteRepository {
        return FavoriteRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun provideMovieDetailRepository(
        api: MovieDetailApiService,
        movieDao: MovieDao,
        networkChecker: NetworkChecker
    ): MovieDetailRepository {
        return MovieDetailRepositoryImpl(api, movieDao, networkChecker)
    }
}