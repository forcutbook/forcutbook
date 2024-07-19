package com.fourcutbook.forcutbook.feature.login

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val LOGIN_ROUTE = "LOGIN"

fun NavController.navigateToLogin(navOptions: NavOptions? = null) {
    navigate(LOGIN_ROUTE, navOptions)
}

fun NavGraphBuilder.loginNavGraph(
    navigateToHome: () -> Unit
) {
    composable(route = LOGIN_ROUTE) {
        LoginRoute(navigateToHome = navigateToHome)
    }
}
