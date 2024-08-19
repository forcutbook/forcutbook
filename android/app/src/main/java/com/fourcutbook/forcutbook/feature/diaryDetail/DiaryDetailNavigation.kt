package com.fourcutbook.forcutbook.feature.diaryDetail

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

const val DIARY_DETAIL_ROUTE = "DIARY_DETAIL_ROUTE"
const val DIARY_ID = "DIARY_ID"

fun NavController.navigateToDiaryDetail(
    diaryId: Long,
    navOptions: NavOptions? = null
) {
    navigate("$DIARY_DETAIL_ROUTE/$diaryId", navOptions)
}

fun NavGraphBuilder.diaryDetailNavGraph(onBackPressed: () -> Unit) {
    composable(
        route = "$DIARY_DETAIL_ROUTE/{$DIARY_ID}",
        arguments = listOf(navArgument(DIARY_ID) { type = NavType.LongType })
//        enterTransition = {
//            slideIntoContainer(
//                towards = AnimatedContentTransitionScope.SlideDirection.Left,
//                animationSpec = tween(700)
//            )
//        }
    ) { navBackStackEntry ->
        val diaryId = navBackStackEntry.arguments?.getLong(DIARY_ID)

        DiaryDetailRoute(
            diaryId = diaryId,
            onBackPressed = onBackPressed
        )
    }
}
