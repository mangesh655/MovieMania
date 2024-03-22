package com.mk.moviemania.navigation.feed.ui

import com.mk.moviemania.common.SealedString
import com.mk.moviemania.exceptions.InvalidFeedViewException

sealed interface FeedView: SealedString {

    data object FeedList: FeedView

    data object FeedGrid: FeedView

    companion object {
        val VALUES = listOf(
            FeedList,
            FeedGrid
        )

        fun transform(name: String): FeedView {
            return when (name) {
                FeedList.toString() -> FeedList
                FeedGrid.toString() -> FeedGrid
                else -> throw InvalidFeedViewException
            }
        }
    }
}