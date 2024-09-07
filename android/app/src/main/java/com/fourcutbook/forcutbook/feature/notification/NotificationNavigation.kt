package com.fourcutbook.forcutbook.feature.notification

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.fourcutbook.forcutbook.feature.FcbRoute

fun NavController.navigateToNotification(navOptions: NavOptions? = null) {
    navigate(FcbRoute.NotificationRoute.value, navOptions)
}

fun NavGraphBuilder.notificationNavGraph(
    onProfileImgClick: (userId: Long) -> Unit,
    onBackClick: () -> Unit,
    onShowSnackBar: (message: String) -> Unit
) {
    composable(route = FcbRoute.NotificationRoute.value) {
        NotificationRoute(
            onBackClick = onBackClick,
            onProfileImgClick = onProfileImgClick,
            onShowSnackBar = onShowSnackBar
        )
    }
}
