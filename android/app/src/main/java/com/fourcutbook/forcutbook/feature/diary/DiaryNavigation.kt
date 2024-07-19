package com.fourcutbook.forcutbook.feature.diary

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.fourcutbook.forcutbook.domain.Diary

const val DIARY_ROUTE = "DIARY_ROUTE"

fun NavController.navigateToDiary(navOptions: NavOptions? = null) {
    navigate(DIARY_ROUTE, navOptions)
}

fun NavGraphBuilder.diaryNavGraph(
    navigateToHome: (diary: Diary) -> Unit,
    onShowSnackBar: (message: String) -> Unit,
) {
    composable(route = DIARY_ROUTE) {
        DiaryRoute(
            onShowSnackBar = onShowSnackBar,
            navigateToHomeScreen = navigateToHome,
        )
    }
}