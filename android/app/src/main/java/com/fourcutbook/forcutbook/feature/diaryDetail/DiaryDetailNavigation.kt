package com.fourcutbook.forcutbook.feature.diaryDetail

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
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

@SuppressLint("UnrememberedGetBackStackEntry")
fun NavGraphBuilder.diaryDetailNavGraph(onBackPressed: () -> Unit) {
    composable(
        route = "$DIARY_DETAIL_ROUTE/{$DIARY_ID}",
        arguments = listOf(navArgument(DIARY_ID) { type = NavType.LongType }),
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(700)
            )
        }
    ) { navBackStackEntry ->
        val diaryId = navBackStackEntry.arguments?.getLong(DIARY_ID)

        DiaryDetailRoute(
            diaryId = diaryId,
            onBackPressed = onBackPressed
        )
    }
}
