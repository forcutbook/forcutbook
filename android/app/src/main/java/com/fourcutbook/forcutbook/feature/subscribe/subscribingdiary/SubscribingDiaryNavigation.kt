package com.fourcutbook.forcutbook.feature.subscribe.subscribingdiary

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.fourcutbook.forcutbook.feature.FcbRoute

const val USER_ID = "USER_ID"

fun NavController.navigateToSubscribingDiary(
    userId: Long? = null,
    navOptions: NavOptions? = null
) {
    navigate("${FcbRoute.SubscribingDiaryRoute.value}/$userId", navOptions)
}

fun NavGraphBuilder.subscribingDiaryNavGraph(
    onUserProfileClick: (userId: Long) -> Unit,
    onBackPressed: () -> Unit,
    onShowSnackBar: (message: String) -> Unit
) {
    composable(
        route = "${FcbRoute.SubscribingDiaryRoute.value}/{$USER_ID}",
        arguments = listOf(navArgument(USER_ID) { type = NavType.LongType })
    ) { navBackStackEntry ->
        val userId = navBackStackEntry.arguments?.getLong(USER_ID)

        SubscribingDiaryRoute(
            userId = userId,
            onUserProfileClick = onUserProfileClick,
            onShowSnackBar = onShowSnackBar,
            onBackPressed = onBackPressed
        )
    }
}
