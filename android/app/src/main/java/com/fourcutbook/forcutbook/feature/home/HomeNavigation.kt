package com.fourcutbook.forcutbook.feature.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val HOME_ROUTE = "HOME_ROUTE"

fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    this.navigate(HOME_ROUTE, navOptions)
}

fun NavGraphBuilder.homeNavGraph(
    navigateToDiaryRegistration: () -> Unit = {},
    navigateToDiaryDetails: () -> Unit = {}
) {
    composable(route = HOME_ROUTE) {
        HomeRoute(
            navigateToDiaryRegistration = navigateToDiaryRegistration,
            navigateToDiaryDetails = navigateToDiaryDetails
        )
    }
}
