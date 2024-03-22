package com.mk.moviemania.persistence.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.mk.moviemania.persistence.database.entity.PagingKeyDb

@Dao
interface PagingKeyDao {

    @Transaction
    @Query("SELECT page FROM pagingkeys WHERE pagingKey = :pagingKey")
    suspend fun page(pagingKey: String): Int?

    @Transaction
    @Query("SELECT totalPages FROM pagingkeys WHERE pagingKey = :pagingKey")
    suspend fun totalPages(pagingKey: String): Int?

    @Transaction
    @Query("DELETE FROM pagingkeys WHERE pagingKey = :pagingKey")
    suspend fun removePagingKey(pagingKey: String)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPagingKey(pagingKey: PagingKeyDb)
}