package com.fourcutbook.forcutbook.feature.followingAndFollower.followerList

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.fourcutbook.forcutbook.feature.FcbRoute

const val USER_ID = "USER_ID"

fun NavController.navigateToFollowerList(
    userId: Long? = null,
    navOptions: NavOptions? = null
) {
    navigate("${FcbRoute.FollowerListRoute.value}/$userId", navOptions)
}

fun NavGraphBuilder.followerListNavGraph(
    onUserProfileClick: (userId: Long) -> Unit,
    onBackPressed: () -> Unit,
    onShowSnackBar: (message: String) -> Unit
) {
    composable(
        route = "${FcbRoute.FollowerListRoute.value}/{$USER_ID}",
        arguments = listOf(navArgument(USER_ID) { type = NavType.LongType })
    ) { navBackStackEntry ->
        val userId = navBackStackEntry.arguments?.getLong(USER_ID)

        FollowerListRoute(
            userId = userId,
            onProfileClick = onUserProfileClick,
            onShowSnackBar = onShowSnackBar,
            onBackPressed = onBackPressed
        )
    }
}
