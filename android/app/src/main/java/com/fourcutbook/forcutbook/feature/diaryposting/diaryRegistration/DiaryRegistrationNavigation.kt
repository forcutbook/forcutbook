package com.fourcutbook.forcutbook.feature.diaryposting.diaryRegistration

import android.annotation.SuppressLint
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.fourcutbook.forcutbook.feature.FcbRoute
import com.fourcutbook.forcutbook.feature.diaryposting.DiaryPostingViewModel

fun NavController.navigateToDiaryRegistration(navOptions: NavOptions? = null) {
    navigate(FcbRoute.DiaryRegistrationRoute.value, navOptions)
}

@SuppressLint("UnrememberedGetBackStackEntry")
fun NavGraphBuilder.diaryRegistrationNavGraph(
    navController: NavController,
    onDiaryRegistered: () -> Unit,
    onBackPressed: () -> Unit,
    onShowSnackBar: (message: String) -> Unit
) {
    composable(route = FcbRoute.DiaryRegistrationRoute.value) {
        val sharedViewModel = navController.getBackStackEntry(FcbRoute.DiaryImageUploadingRoute.value).run {
            hiltViewModel<DiaryPostingViewModel>(this)
        }
        DiaryRegistrationRoute(
            diaryPostingViewModel = sharedViewModel,
            onDiaryRegistered = onDiaryRegistered,
            onShowSnackBar = onShowSnackBar,
            onBackPressed = onBackPressed
        )
    }
}
