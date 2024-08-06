package com.fourcutbook.forcutbook.feature.subscribe.subscribeduser

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.fourcutbook.forcutbook.feature.FcbRoute

fun NavController.navigateToSubscribedUser(navOptions: NavOptions? = null) {
    navigate(FcbRoute.SubscribedUserRoute.value, navOptions)
}

fun NavGraphBuilder.subscribedUserNavGraph(
    navigateToUserPage: () -> Unit,
    onBackPressed: () -> Unit,
    onShowSnackBar: (message: String) -> Unit
) {
    composable(route = FcbRoute.SubscribingDiaryRoute.value) {
        SubscribedUserRoute(
            navigateToUserPage = navigateToUserPage,
            onShowSnackBar = onShowSnackBar,
            onBackPressed = onBackPressed
        )
    }
}
