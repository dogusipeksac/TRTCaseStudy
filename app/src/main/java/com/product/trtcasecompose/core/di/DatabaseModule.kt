package com.product.trtcasecompose.core.di

import android.app.Application
import androidx.room.Room
import com.product.trtcasecompose.features.favorite.data.local.dao.FavoriteMovieDao
import com.product.trtcasecompose.core.database.AppDatabase
import com.product.trtcasecompose.features.home.data.local.dao.MovieDao
import dagger.Provides
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(app, AppDatabase::class.java, "app_db").build()
    }

    @Provides
    @Singleton
    fun provideFavoriteMovieDao(database: AppDatabase): FavoriteMovieDao {
        return database.favoriteMovieDao()
    }

    @Provides
    @Singleton
    fun provideMovieDao(database: AppDatabase): MovieDao {
        return database.movieDao()
    }
}

