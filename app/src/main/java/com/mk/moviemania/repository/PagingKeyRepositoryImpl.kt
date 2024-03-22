package com.mk.moviemania.repository

import com.mk.moviemania.persistence.database.dao.PagingKeyDao
import com.mk.moviemania.persistence.database.entity.PagingKeyDb
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class PagingKeyRepositoryImpl @Inject constructor(
    private val pagingKeyDao: PagingKeyDao
): PagingKeyRepository {

    override suspend fun page(pagingKey: String): Int? {
        return pagingKeyDao.page(pagingKey)
    }

    override suspend fun totalPages(pagingKey: String): Int? {
        return pagingKeyDao.totalPages(pagingKey)
    }

    override suspend fun prevPage(pagingKey: String): Int? {
        return null
    }

    override suspend fun removePagingKey(pagingKey: String) {
        pagingKeyDao.removePagingKey(pagingKey)
    }

    override suspend fun insertPagingKey(pagingKey: String, page: Int, totalPages: Int) {
        pagingKeyDao.insertPagingKey(
            PagingKeyDb(
                pagingKey = pagingKey,
                page = page,
                totalPages = totalPages
            )
        )
    }
}