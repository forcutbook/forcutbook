package com.fourcutbook.forcutbook.feature.imageUploading

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.fourcutbook.forcutbook.feature.FcbRoute

fun NavController.navigateToImageUploading(navOptions: NavOptions? = null) {
    navigate(FcbRoute.DIARY_IMAGE_UPLOADING_ROUTE.value, navOptions)
}

fun NavGraphBuilder.imageUploadingNavGraph(
    navigateToDiaryScreen: () -> Unit
) {
    composable(route = FcbRoute.DIARY_IMAGE_UPLOADING_ROUTE.value) {
        ImageUploadingRoute {
            navigateToDiaryScreen()
        }
    }
}
