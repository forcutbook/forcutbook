package com.fourcutbook.forcutbook.feature.mypage

import android.annotation.SuppressLint
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.fourcutbook.forcutbook.feature.FcbRoute

fun NavController.navigateToMyPage(
    navOptions: NavOptions? = null
) {
    navigate(FcbRoute.MyPageRoute.value, navOptions)
}

@SuppressLint("UnrememberedGetBackStackEntry")
fun NavGraphBuilder.myPageNavGraph(
    onSubscribingUserClick: (userId: Long) -> Unit,
    onSubscribedUserClick: (userId: Long) -> Unit,
    onDiaryClick: (diaryId: Long) -> Unit,
    onShowSnackBar: (message: String) -> Unit
) {
    composable(route = FcbRoute.MyPageRoute.value) {
        MyPageRoute(
            onSubscribingUserClick = onSubscribingUserClick,
            onSubscribedUserClick = onSubscribedUserClick,
            onDiaryClick = onDiaryClick
        )
    }
}
