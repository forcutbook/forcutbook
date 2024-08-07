package com.fourcutbook.forcutbook.feature.diaryfeed

import android.annotation.SuppressLint
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.fourcutbook.forcutbook.feature.FcbRoute
import kotlinx.coroutines.flow.forEach

fun NavController.navigateToDiaryFeed(navOptions: NavOptions? = null) {
    this.navigate(FcbRoute.DiaryFeed.value, navOptions)
}

@SuppressLint("UnrememberedGetBackStackEntry")
fun NavGraphBuilder.diaryFeedNavGraph(
    navController: NavController,
    navigateToDiaryDetails: (diaryId: Long) -> Unit = { }
) {
    navController.currentBackStack.value.forEach {
        Log.d("woogi", "diaryFeedNavGraph: ${it.destination}")
    }
    composable(route = FcbRoute.DiaryFeed.value) {
        DiaryFeedRoute(
            navigateToDiaryDetail = navigateToDiaryDetails
        )
    }
}
