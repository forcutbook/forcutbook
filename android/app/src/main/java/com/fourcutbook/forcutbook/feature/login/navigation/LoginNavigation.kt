package com.fourcutbook.forcutbook.feature.login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.fourcutbook.forcutbook.feature.FcbRoute
import com.fourcutbook.forcutbook.feature.login.LoginRoute

fun NavController.navigateToLogin(navOptions: NavOptions? = null) {
    navigate(FcbRoute.LoginRoute.value, navOptions)
}

fun NavGraphBuilder.loginNavGraph(
    navigateToHome: () -> Unit
) {
    composable(route = FcbRoute.LoginRoute.value) {
        LoginRoute(navigateToHome = navigateToHome)
    }
}