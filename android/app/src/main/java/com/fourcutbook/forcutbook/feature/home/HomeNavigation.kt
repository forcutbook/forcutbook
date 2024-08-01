package com.fourcutbook.forcutbook.feature.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.fourcutbook.forcutbook.feature.FcbRoute

fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    this.navigate(FcbRoute.HomeRoute.value, navOptions)
}

fun NavGraphBuilder.homeNavGraph(
    navigateToDiaryRegistration: () -> Unit = {},
    navigateToDiaryDetails: () -> Unit = {}
) {
    composable(route = FcbRoute.HomeRoute.value) {
        HomeRoute(
            navigateToDiaryRegistration = navigateToDiaryRegistration,
            navigateToDiaryDetail = navigateToDiaryDetails
        )
    }
}
