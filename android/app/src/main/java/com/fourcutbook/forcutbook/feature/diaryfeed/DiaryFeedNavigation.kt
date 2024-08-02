package com.fourcutbook.forcutbook.feature.diaryfeed

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.fourcutbook.forcutbook.feature.FcbRoute

fun NavController.navigateToDiaryFeed(navOptions: NavOptions? = null) {
    this.navigate(FcbRoute.DiaryFeed.value, navOptions)
}

fun NavGraphBuilder.diaryFeedNavGraph(
    navigateToDiaryRegistration: () -> Unit = {},
    navigateToDiaryDetails: () -> Unit = {}
) {
    composable(route = FcbRoute.DiaryFeed.value) {
        DiaryFeedRoute(
            navigateToDiaryRegistration = navigateToDiaryRegistration,
            navigateToDiaryDetail = navigateToDiaryDetails
        )
    }
}
