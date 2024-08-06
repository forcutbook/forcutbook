package com.fourcutbook.forcutbook.feature.mypage

import android.annotation.SuppressLint
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.fourcutbook.forcutbook.feature.FcbRoute
import com.fourcutbook.forcutbook.feature.diaryDetail.navigateToDiaryDetail

fun NavController.navigateToMyPage(navOptions: NavOptions? = null) {
    currentBackStack.value.forEach {
        Log.d("woogi", "navigateToMyPage: $it")
    }
    navigate(FcbRoute.MyPageRoute.value, navOptions)
}

@SuppressLint("UnrememberedGetBackStackEntry")
fun NavGraphBuilder.myPageNavGraph(
    navController: NavController,
    navigateToSubscribingDiary: () -> Unit,
    onShowSnackBar: (message: String) -> Unit
) {
    composable(route = FcbRoute.MyPageRoute.value) {
        MyPageRoute(
            navigateToSubscribingDiaryScreen = navigateToSubscribingDiary,
            navigateToSubscribedUserScreen = {},
            navigateToDiaryDetailScreen = navController::navigateToDiaryDetail
        )
    }
}
