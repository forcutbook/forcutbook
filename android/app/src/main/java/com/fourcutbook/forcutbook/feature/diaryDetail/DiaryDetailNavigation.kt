package com.fourcutbook.forcutbook.feature.diaryDetail

import android.annotation.SuppressLint
import android.util.Log
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.fourcutbook.forcutbook.feature.FcbRoute
import com.fourcutbook.forcutbook.feature.home.HomeViewModel
import kotlinx.coroutines.flow.toList

const val DIARY_DETAIL_ROUTE = "DIARY_DETAIL_ROUTE"

fun NavController.navigateToDiaryDetail(navOptions: NavOptions? = null) {
    navigate(DIARY_DETAIL_ROUTE, navOptions)
}

@SuppressLint("UnrememberedGetBackStackEntry")
fun NavGraphBuilder.diaryDetailNavGraph(
    navController: NavController,
    onBackPressed: () -> Unit,
    onShowSnackBar: (message: String) -> Unit
) {
    composable(route = DIARY_DETAIL_ROUTE) {
        val sharedViewModel = navController.getBackStackEntry(FcbRoute.HomeRoute.value).run {
            hiltViewModel<HomeViewModel>(this)
        }
        DiaryDetailRoute(
            diaryViewModel = sharedViewModel,
            onBackPressed = {
                onBackPressed()
                Log.d("woogi", "diaryRegistrationNavGraph: ${navController.currentBackStack.value.toList()}")
            }
        )
    }
}
