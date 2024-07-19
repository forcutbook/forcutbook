package com.fourcutbook.forcutbook.feature.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.fourcutbook.forcutbook.domain.Diary
import java.time.LocalDateTime

const val HOME_ROUTE = "HOME_ROUTE"

fun NavController.navigateToHome(
    diary: Diary? = null,
    navOptions: NavOptions? = null,
) {
    this.navigate("$HOME_ROUTE/${diary?.title ?: "X"} /${diary?.contents ?: "X"}/${diary?.date ?: "X"}")
}

fun NavGraphBuilder.homeNavGraph() {
    composable(
        route = "$HOME_ROUTE/{title}/{body}/{dateTime}",
        arguments = listOf(
            navArgument("title") { type = NavType.StringType },
            navArgument("body") { type = NavType.StringType },
            navArgument("dateTime") { type = NavType.StringType }
        )
    ) { navBackStackEntry ->
        val title = navBackStackEntry.arguments?.getString("title")

        if(title == "X" || title == null) HomeRoute()
        else {
            val body = navBackStackEntry.arguments?.getString("body")
            val dateTime = navBackStackEntry.arguments?.getString("dateTime")
            HomeRoute(newDiary = Diary(title = title, contents = body!!, date = LocalDateTime.parse(dateTime)))
        }
    }
}

