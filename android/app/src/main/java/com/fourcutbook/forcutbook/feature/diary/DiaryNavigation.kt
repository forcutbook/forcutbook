package com.fourcutbook.forcutbook.feature.diary

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val DIARY_ROUTE = "DIARY_ROUTE"

fun NavController.navigateToDiary(navOptions: NavOptions? = null) {
    navigate(DIARY_ROUTE, navOptions)
}

fun NavGraphBuilder.diaryNavGraph(
    onShowSnackBar: (message: String) -> Unit
) {
    composable(route = DIARY_ROUTE) {
        DiaryRoute(onShowSnackBar = onShowSnackBar)
    }
}
