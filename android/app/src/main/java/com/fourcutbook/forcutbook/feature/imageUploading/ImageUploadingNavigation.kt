package com.fourcutbook.forcutbook.feature.imageUploading

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val IMAGE_UPLOADING_ROUTE = "IMAGE_UPLOADING_ROUTE"

fun NavController.navigateToImageUploading(navOptions: NavOptions? = null) {
    navigate(IMAGE_UPLOADING_ROUTE, navOptions)
}

fun NavGraphBuilder.imageUploadingNavGraph(
    navigateToDiaryScreen: () -> Unit
) {
    composable(route = IMAGE_UPLOADING_ROUTE) {
        ImageUploadingRoute {
            navigateToDiaryScreen()
        }
    }
}
