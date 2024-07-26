package com.fourcutbook.forcutbook.feature.diaryDetail

import android.annotation.SuppressLint
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.fourcutbook.forcutbook.feature.home.HOME_ROUTE
import com.fourcutbook.forcutbook.feature.home.HomeViewModel

const val DIARY_DETAIL_ROUTE = "DIARY_DETAIL_ROUTE"

fun NavController.navigateToDiaryDetail(navOptions: NavOptions? = null) {
    navigate(DIARY_DETAIL_ROUTE, navOptions)
}

@SuppressLint("UnrememberedGetBackStackEntry")
fun NavGraphBuilder.diaryDetailNavGraph(
    navController: NavController,
    onShowSnackBar: (message: String) -> Unit
) {
    composable(route = DIARY_DETAIL_ROUTE) {
        val sharedViewModel = navController.getBackStackEntry(HOME_ROUTE).run {
            hiltViewModel<HomeViewModel>(this)
        }
        DiaryDetailRoute(diaryViewModel = sharedViewModel)
    }
}
