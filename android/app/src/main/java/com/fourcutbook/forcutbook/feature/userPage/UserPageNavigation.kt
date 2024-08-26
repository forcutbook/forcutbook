package com.fourcutbook.forcutbook.feature.userPage

import android.annotation.SuppressLint
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.fourcutbook.forcutbook.feature.FcbRoute

const val USER_ID = "USER_ID"

fun NavController.navigateToUserPage(
    userId: Long? = null,
    navOptions: NavOptions? = null
) {
    navigate("${FcbRoute.UserPageRoute.value}/$userId", navOptions)
}

@SuppressLint("UnrememberedGetBackStackEntry")
fun NavGraphBuilder.userPageNavGraph(
    onFollowingCountClick: (userId: Long?) -> Unit,
    onFollowerCountClick: (userId: Long?) -> Unit,
    onDiaryClick: (diaryId: Long) -> Unit,
    onBackClick: () -> Unit,
    onShowSnackBar: (message: String) -> Unit
) {
    composable(
        route = "${FcbRoute.UserPageRoute.value}/{$USER_ID}",
        arguments = listOf(navArgument(USER_ID) { type = NavType.LongType })
    ) { navBackStackEntry ->
        val userId = navBackStackEntry.arguments?.getLong(USER_ID)

        UserPageRoute(
            userId = userId,
            onFollowingCountClick = onFollowingCountClick,
            onFollowerCountClick = onFollowerCountClick,
            onDiaryClick = onDiaryClick,
            onBackClick = onBackClick
        )
    }
}
