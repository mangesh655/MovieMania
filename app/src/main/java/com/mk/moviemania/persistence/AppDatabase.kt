package com.mk.moviemania.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mk.moviemania.persistence.database.converter.CalendarConverter
import com.mk.moviemania.persistence.database.dao.MovieDao
import com.mk.moviemania.persistence.database.dao.PagingKeyDao
import com.mk.moviemania.persistence.database.entity.MovieDb
import com.mk.moviemania.persistence.database.entity.PagingKeyDb

/**
 * The Room database for this app.
 */
@Database(
    entities = [
        MovieDb::class,
        PagingKeyDb::class,
    ],
    version = AppDatabase.DATABASE_VERSION,
    exportSchema = false
)
@TypeConverters(CalendarConverter::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun movieDao(): MovieDao
    abstract fun pagingKeyDao(): PagingKeyDao

    companion object {
        private val DATABASE_NAME = "movies-db"
        const val DATABASE_VERSION = 21

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}