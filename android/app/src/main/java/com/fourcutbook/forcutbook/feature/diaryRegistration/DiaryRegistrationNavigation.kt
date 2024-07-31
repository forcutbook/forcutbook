package com.fourcutbook.forcutbook.feature.diaryRegistration

import android.annotation.SuppressLint
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.fourcutbook.forcutbook.feature.FcbRoute
import com.fourcutbook.forcutbook.feature.imageUploading.ImageUploadingViewModel

fun NavController.navigateToDiaryRegistration(navOptions: NavOptions? = null) {
    navigate(FcbRoute.DIARY_REGISTRATION_ROUTE.value, navOptions)
}

@SuppressLint("UnrememberedGetBackStackEntry")
fun NavGraphBuilder.diaryRegistrationNavGraph(
    navController: NavController,
    navigateToHomeScreen: () -> Unit,
    onShowSnackBar: (message: String) -> Unit
) {
    composable(route = FcbRoute.DIARY_REGISTRATION_ROUTE.value) {
        val sharedViewModel = navController.getBackStackEntry(FcbRoute.DIARY_IMAGE_UPLOADING_ROUTE.value).run {
            hiltViewModel<ImageUploadingViewModel>(this)
        }
        DiaryRegistrationRoute(
            imageUploadingViewModel = sharedViewModel,
            navigateToHomeScreen = navigateToHomeScreen,
            onShowSnackBar = onShowSnackBar
        )
    }
}
