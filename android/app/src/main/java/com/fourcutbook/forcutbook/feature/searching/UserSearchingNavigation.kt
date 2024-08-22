package com.fourcutbook.forcutbook.feature.searching

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.fourcutbook.forcutbook.feature.FcbRoute

fun NavController.navigateToUserSearching(navOptions: NavOptions? = null) {
    navigate(FcbRoute.UserSearchingRoute.value, navOptions)
}

fun NavGraphBuilder.userSearchingNavGraph(
    onUserProfileClick: (userId: Long) -> Unit,
    onBackClick: () -> Unit,
    onShowSnackBar: (message: String) -> Unit
) {
    composable(route = FcbRoute.UserSearchingRoute.value) {
        UserSearchingRoute(
            onUserProfileClick = onUserProfileClick,
            onBackClick = onBackClick
        )
    }
}
