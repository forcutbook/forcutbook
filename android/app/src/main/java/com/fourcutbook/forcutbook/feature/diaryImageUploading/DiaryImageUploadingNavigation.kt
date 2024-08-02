package com.fourcutbook.forcutbook.feature.diaryImageUploading

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.fourcutbook.forcutbook.feature.FcbRoute

fun NavController.navigateToDiaryImageUploading(navOptions: NavOptions? = null) {
    navigate(FcbRoute.DiaryImageUploadingRoute.value, navOptions)
}

fun NavGraphBuilder.diaryImageUploadingNavGraph(
    navigateToDiaryScreen: () -> Unit
) {
    composable(route = FcbRoute.DiaryImageUploadingRoute.value) {
        DiaryImageUploadingRoute {
            navigateToDiaryScreen()
        }
    }
}
