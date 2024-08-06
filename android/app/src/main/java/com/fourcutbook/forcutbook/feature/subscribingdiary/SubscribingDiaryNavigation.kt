package com.fourcutbook.forcutbook.feature.subscribingdiary

import android.annotation.SuppressLint
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.fourcutbook.forcutbook.feature.FcbRoute

fun NavController.navigateToSubscribingDiary(navOptions: NavOptions? = null) {
    navigate(FcbRoute.SubscribingDiaryRoute.value, navOptions)
}

@SuppressLint("UnrememberedGetBackStackEntry")
fun NavGraphBuilder.subscribingDiaryNavGraph(
    navigateToUserPage: () -> Unit,
    onBackPressed: () -> Unit,
    onShowSnackBar: (message: String) -> Unit
) {
    composable(route = FcbRoute.SubscribingDiaryRoute.value) {
        SubscribingDiaryRoute(
            navigateToUserPage = navigateToUserPage,
            onShowSnackBar = onShowSnackBar,
            onBackPressed = onBackPressed
        )
    }
}
