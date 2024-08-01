package com.fourcutbook.forcutbook.feature.diaryRegistration

import android.annotation.SuppressLint
import android.util.Log
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.fourcutbook.forcutbook.feature.FcbRoute
import com.fourcutbook.forcutbook.feature.imageUploading.ImageUploadingViewModel

fun NavController.navigateToDiaryRegistration(navOptions: NavOptions? = null) {
    navigate(FcbRoute.DiaryRegistrationRoute.value, navOptions)
}

@SuppressLint("UnrememberedGetBackStackEntry")
fun NavGraphBuilder.diaryRegistrationNavGraph(
    navController: NavController,
    navigateToHomeScreen: () -> Unit,
    onBackPressed: () -> Unit,
    onShowSnackBar: (message: String) -> Unit
) {
    composable(route = FcbRoute.DiaryRegistrationRoute.value) {
        val sharedViewModel = navController.getBackStackEntry(FcbRoute.DiaryImageUploadingRoute.value).run {
            hiltViewModel<ImageUploadingViewModel>(this)
        }
        DiaryRegistrationRoute(
            imageUploadingViewModel = sharedViewModel,
            navigateToHomeScreen = navigateToHomeScreen,
            onShowSnackBar = onShowSnackBar,
            onBackPressed = {
                onBackPressed()
                Log.d("woogi", "diaryRegistrationNavGraph: ${navController.currentBackStack}")
            }
        )
    }
}
