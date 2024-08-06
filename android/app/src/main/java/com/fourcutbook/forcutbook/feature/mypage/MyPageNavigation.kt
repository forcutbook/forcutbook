package com.fourcutbook.forcutbook.feature.mypage

import android.annotation.SuppressLint
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.fourcutbook.forcutbook.feature.FcbRoute
import com.fourcutbook.forcutbook.feature.diaryDetail.navigateToDiaryDetail

fun NavController.navigateToMyPage(navOptions: NavOptions? = null) {
    navigate(FcbRoute.MyPageRoute.value, navOptions)
}

@SuppressLint("UnrememberedGetBackStackEntry")
fun NavGraphBuilder.myPageNavGraph(
    navController: NavController,
    navigateToSubscribingDiary: () -> Unit,
    navigateToSubscribedUser: () -> Unit,
    onShowSnackBar: (message: String) -> Unit
) {
    composable(route = FcbRoute.MyPageRoute.value) {
        MyPageRoute(
            navigateToSubscribingDiaryScreen = navigateToSubscribingDiary,
            navigateToSubscribedUserScreen = navigateToSubscribedUser,
            navigateToDiaryDetailScreen = navController::navigateToDiaryDetail
        )
    }
}
