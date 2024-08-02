package com.fourcutbook.forcutbook.feature.diaryfeed.diaryDetail

import android.annotation.SuppressLint
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.fourcutbook.forcutbook.feature.FcbRoute
import com.fourcutbook.forcutbook.feature.diaryfeed.DiaryFeedViewModel

const val DIARY_DETAIL_ROUTE = "DIARY_DETAIL_ROUTE"

fun NavController.navigateToDiaryDetail(navOptions: NavOptions? = null) {
    navigate(DIARY_DETAIL_ROUTE, navOptions)
}

@SuppressLint("UnrememberedGetBackStackEntry")
fun NavGraphBuilder.diaryDetailNavGraph(
    navController: NavController,
    navigateToDiaryFeed: () -> Unit,
    onBackPressed: () -> Unit,
    onShowSnackBar: (message: String) -> Unit
) {
    composable(route = DIARY_DETAIL_ROUTE) {
        val sharedViewModel = navController.getBackStackEntry(FcbRoute.DiaryFeed.value).run {
            hiltViewModel<DiaryFeedViewModel>(this)
        }
        DiaryDetailRoute(
            diaryFeedViewModel = sharedViewModel,
            navigateToDiaryFeed = navigateToDiaryFeed,
            onBackPressed = onBackPressed
        )
    }
}
