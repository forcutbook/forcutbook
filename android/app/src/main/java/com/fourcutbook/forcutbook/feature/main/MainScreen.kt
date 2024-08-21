package com.fourcutbook.forcutbook.feature.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.fourcutbook.forcutbook.design.FcbTheme
import com.fourcutbook.forcutbook.feature.FcbBottomNavigation
import com.fourcutbook.forcutbook.feature.FcbRoute
import com.fourcutbook.forcutbook.feature.diaryDetail.diaryDetailNavGraph
import com.fourcutbook.forcutbook.feature.diaryDetail.navigateToDiaryDetail
import com.fourcutbook.forcutbook.feature.diaryfeed.diaryFeedNavGraph
import com.fourcutbook.forcutbook.feature.diaryfeed.navigateToDiaryFeed
import com.fourcutbook.forcutbook.feature.diaryposting.diaryImageUploading.diaryImageUploadingNavGraph
import com.fourcutbook.forcutbook.feature.diaryposting.diaryRegistration.diaryRegistrationNavGraph
import com.fourcutbook.forcutbook.feature.diaryposting.diaryRegistration.navigateToDiaryRegistration
import com.fourcutbook.forcutbook.feature.login.navigation.loginNavGraph
import com.fourcutbook.forcutbook.feature.mypage.myPageNavGraph
import com.fourcutbook.forcutbook.feature.notification.navigateToNotification
import com.fourcutbook.forcutbook.feature.notification.notificationNavGraph
import com.fourcutbook.forcutbook.feature.subscribe.subscribeduser.navigateToSubscribedUser
import com.fourcutbook.forcutbook.feature.subscribe.subscribeduser.subscribedUserNavGraph
import com.fourcutbook.forcutbook.feature.subscribe.subscribingdiary.navigateToSubscribingDiary
import com.fourcutbook.forcutbook.feature.subscribe.subscribingdiary.subscribingDiaryNavGraph
import com.fourcutbook.forcutbook.feature.userPage.navigateToUserPage
import com.fourcutbook.forcutbook.feature.userPage.userPageNavGraph
import kotlinx.coroutines.launch

@Composable
fun MainScreen(navController: NavHostController = rememberNavController()) {
    val coroutineScope = rememberCoroutineScope()
    val snackBarHostState: SnackbarHostState = remember { SnackbarHostState() }
    val onShowSnackBar: (message: String) -> Unit = { message ->
        coroutineScope.launch {
            snackBarHostState.showSnackbar(message)
        }
    }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(FcbTheme.colors.fcbGray),
        bottomBar = { FcbBottomNavigation(navController = navController) }
    ) { contentPadding ->

        NavHost(
            modifier = Modifier
                .background(color = FcbTheme.colors.fcbGray)
                .padding(
                    top = contentPadding.calculateTopPadding(),
                    bottom = contentPadding.calculateBottomPadding(),
                    start = FcbTheme.padding.basicHorizontalPadding,
                    end = FcbTheme.padding.basicHorizontalPadding
                )
                .fillMaxSize(),
            navController = navController,
            startDestination = FcbRoute.LoginRoute.value
        ) {
            loginNavGraph(navigateToHome = navController::navigateToDiaryFeed)

            diaryFeedNavGraph(
                onDiaryClick = navController::navigateToDiaryDetail,
                onNotificationClick = navController::navigateToNotification
            )

            diaryImageUploadingNavGraph(navigateToDiaryScreen = navController::navigateToDiaryRegistration)

            diaryRegistrationNavGraph(
                navController = navController,
                onDiaryRegistered = navController::navigateToDiaryFeed,
                onBackPressed = navController::popBackStack,
                onShowSnackBar = onShowSnackBar
            )

            diaryDetailNavGraph(onBackPressed = navController::popBackStack)

            myPageNavGraph(
                onSubscribingUserClick = navController::navigateToSubscribingDiary,
                onSubscribedUserClick = navController::navigateToSubscribedUser,
                onDiaryClick = navController::navigateToDiaryDetail,
                onShowSnackBar = onShowSnackBar
            )

            subscribingDiaryNavGraph(
                onUserProfileClick = navController::navigateToUserPage,
                onBackPressed = navController::popBackStack,
                onShowSnackBar = onShowSnackBar
            )

            subscribedUserNavGraph(
                onUserProfileClick = navController::navigateToUserPage,
                onBackPressed = navController::popBackStack,
                onShowSnackBar = onShowSnackBar
            )

            notificationNavGraph(
                onProfileImgClick = navController::navigateToUserPage,
                onBackClick = navController::popBackStack
            )

            myPageNavGraph(
                onSubscribingUserClick = navController::navigateToSubscribingDiary,
                onSubscribedUserClick = navController::navigateToSubscribedUser,
                onDiaryClick = navController::navigateToDiaryDetail,
                onShowSnackBar = onShowSnackBar
            )

            userPageNavGraph(
                onSubscribingUserClick = navController::navigateToSubscribingDiary,
                onSubscribedUserClick = navController::navigateToSubscribedUser,
                onDiaryClick = navController::navigateToDiaryDetail,
                onBackClick = navController::popBackStack,
                onShowSnackBar = onShowSnackBar
            )
        }
    }
}

@Preview(widthDp = 360, heightDp = 640)
@Composable
fun MainPreview() {
    MainScreen()
}
