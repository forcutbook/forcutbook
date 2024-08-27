package com.fourcutbook.forcutbook.feature.diaryposting.diaryRegistration

import android.annotation.SuppressLint
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.fourcutbook.forcutbook.feature.FcbRoute

private const val FILE_PATH = "FILE_PATH"
private const val TITLE = "TITLE"
private const val CONTENTS = "CONTENTS"

fun NavController.navigateToDiaryRegistration(
    title: String,
    contents: String,
    filePath: String,
    navOptions: NavOptions? = null
) {
    title.replace(" ", "%20")
    contents.replace(" ", "%20")
    filePath.replace("/", "_")
    navigate("${FcbRoute.DiaryRegistrationRoute.value}/${ title.replace(" ", "%20")}/${contents.replace(" ", "%20")}/${filePath.replace("/", "_")}", navOptions)
}

@SuppressLint("UnrememberedGetBackStackEntry")
fun NavGraphBuilder.diaryRegistrationNavGraph(
    navController: NavController,
    onDiaryRegistered: () -> Unit,
    onBackPressed: () -> Unit,
    onShowSnackBar: (message: String) -> Unit
) {
    composable(
        route = "${FcbRoute.DiaryRegistrationRoute.value}/{$TITLE}/{$CONTENTS}/{$FILE_PATH}",
        arguments = listOf(
            navArgument(TITLE) { type = NavType.StringType },
            navArgument(CONTENTS) { type = NavType.StringType },
            navArgument(FILE_PATH) { type = NavType.StringType }
        )
    ) { navBackStackEntry ->
        runCatching {
            val title = navBackStackEntry
                .arguments
                ?.getString(TITLE)
                ?: throw IllegalArgumentException("다이어리를 등록할 수 없습니다.")

            val contents = navBackStackEntry
                .arguments
                ?.getString(CONTENTS)
                ?: throw IllegalArgumentException("다이어리를 등록할 수 없습니다.")

            val filePath = navBackStackEntry
                .arguments
                ?.getString(FILE_PATH)
                ?.replace("_", "/")
                ?: throw IllegalArgumentException("다이어리를 등록할 수 없습니다.")

            DiaryRegistrationRoute(
                title = title,
                contents = contents,
                filePath = filePath,
                onDiaryRegistered = onDiaryRegistered,
                onShowSnackBar = onShowSnackBar,
                onBackPressed = onBackPressed
            )
        }.onFailure {
            it.message?.let { message ->
                onShowSnackBar(message)
            }
        }
    }
}
