package com.fourcutbook.forcutbook.feature.followingAndFollower.followingList

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.fourcutbook.forcutbook.feature.FcbRoute

const val USER_ID = "USER_ID"

fun NavController.navigateToFollowingList(
    userId: Long? = null,
    navOptions: NavOptions? = null
) {
    navigate("${FcbRoute.FollowingListRoute.value}/$userId", navOptions)
}

fun NavGraphBuilder.followingListNavGraph(
    onUserProfileClick: (userId: Long) -> Unit,
    onBackPressed: () -> Unit,
    onShowSnackBar: (message: String) -> Unit
) {
    composable(
        route = "${FcbRoute.FollowingListRoute.value}/{$USER_ID}",
        arguments = listOf(navArgument(USER_ID) { type = NavType.LongType })
    ) { navBackStackEntry ->
        val userId = navBackStackEntry.arguments?.getLong(USER_ID)

        FollowingListRoute(
            userId = userId,
            onUserProfileClick = onUserProfileClick,
            onShowSnackBar = onShowSnackBar,
            onBackPressed = onBackPressed
        )
    }
}
