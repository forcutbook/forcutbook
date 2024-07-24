package com.fourcutbook.forcutbook.feature.diaryRegistration

import android.annotation.SuppressLint
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.fourcutbook.forcutbook.feature.imageUploading.IMAGE_UPLOADING_ROUTE
import com.fourcutbook.forcutbook.feature.imageUploading.ImageUploadingViewModel

const val DIARY_REGISTRATION_ROUTE = "DIARY_REGISTRATION_ROUTE"

fun NavController.navigateToDiaryRegistration(navOptions: NavOptions? = null) {
    navigate(DIARY_REGISTRATION_ROUTE, navOptions)
}

@SuppressLint("UnrememberedGetBackStackEntry")
fun NavGraphBuilder.diaryRegistrationNavGraph(
    navController: NavController,
    onShowSnackBar: (message: String) -> Unit
) {
    composable(route = DIARY_REGISTRATION_ROUTE) {
        val sharedViewModel = navController.getBackStackEntry(IMAGE_UPLOADING_ROUTE).run {
            hiltViewModel<ImageUploadingViewModel>(this)
        }
        DiaryRegistrationRoute(
            imageUploadingViewModel = sharedViewModel,
            onShowSnackBar = onShowSnackBar
        )
    }
}
