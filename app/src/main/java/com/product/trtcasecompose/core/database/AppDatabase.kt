package com.product.trtcasecompose.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.product.trtcasecompose.features.favorite.data.local.dao.FavoriteMovieDao
import com.product.trtcasecompose.features.favorite.data.local.entity.FavoriteMovieEntity
import com.product.trtcasecompose.features.home.data.local.dao.MovieDao
import com.product.trtcasecompose.features.home.data.local.entity.MovieEntity

@Database(
    entities = [FavoriteMovieEntity::class, MovieEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteMovieDao(): FavoriteMovieDao
    abstract fun movieDao(): MovieDao
}