package com.fourcutbook.forcutbook.feature.diary

import android.annotation.SuppressLint
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.fourcutbook.forcutbook.feature.diaryRegstration.DIARY_REGISTRATION_ROUTE
import com.fourcutbook.forcutbook.feature.diaryRegstration.DiaryRegistrationViewModel

const val DIARY_ROUTE = "DIARY_ROUTE"

fun NavController.navigateToDiary(navOptions: NavOptions? = null) {
    navigate(DIARY_ROUTE, navOptions)
}

@SuppressLint("UnrememberedGetBackStackEntry")
fun NavGraphBuilder.diaryNavGraph(
    navController: NavController,
    onShowSnackBar: (message: String) -> Unit
) {
    composable(route = DIARY_ROUTE) {
        val sharedViewModel = navController.getBackStackEntry(DIARY_REGISTRATION_ROUTE).run {
            hiltViewModel<DiaryRegistrationViewModel>(this)
        }
        DiaryRoute(
            diaryRegistrationViewModel = sharedViewModel,
            onShowSnackBar = onShowSnackBar
        )
    }
}
