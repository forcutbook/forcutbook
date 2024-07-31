package com.fourcutbook.forcutbook.feature.login

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.fourcutbook.forcutbook.feature.FcbRoute

fun NavController.navigateToLogin(navOptions: NavOptions? = null) {
    navigate(FcbRoute.LOGIN_ROUTE.value, navOptions)
}

fun NavGraphBuilder.loginNavGraph(
    navigateToHome: () -> Unit
) {
    composable(route = FcbRoute.LOGIN_ROUTE.value) {
        LoginRoute(navigateToHome = navigateToHome)
    }
}
