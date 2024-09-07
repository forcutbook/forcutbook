package com.fourcutbook.forcutbook.feature.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
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
import com.fourcutbook.forcutbook.feature.followingAndFollower.followerList.followerListNavGraph
import com.fourcutbook.forcutbook.feature.followingAndFollower.followerList.navigateToFollowerList
import com.fourcutbook.forcutbook.feature.followingAndFollower.followingList.followingListNavGraph
import com.fourcutbook.forcutbook.feature.followingAndFollower.followingList.navigateToFollowingList
import com.fourcutbook.forcutbook.feature.login.navigation.loginNavGraph
import com.fourcutbook.forcutbook.feature.mypage.MyPageViewModel
import com.fourcutbook.forcutbook.feature.mypage.myPageNavGraph
import com.fourcutbook.forcutbook.feature.notification.navigateToNotification
import com.fourcutbook.forcutbook.feature.notification.notificationNavGraph
import com.fourcutbook.forcutbook.feature.searching.userSearchingNavGraph
import com.fourcutbook.forcutbook.feature.userPage.navigateToUserPage
import com.fourcutbook.forcutbook.feature.userPage.userPageNavGraph
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    navController: NavHostController = rememberNavController(),
    mainViewModel: MainViewModel = hiltViewModel(),
    myPageViewModel: MyPageViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val snackBarHostState: SnackbarHostState = remember { SnackbarHostState() }
    val onShowSnackBar: (message: String) -> Unit = { message ->
        coroutineScope.launch {
            snackBarHostState.showSnackbar(message)
        }
    }
    val uiState by mainViewModel.uiState.collectAsStateWithLifecycle()

    val startDestination = when (uiState) {
        is MainUiState.SignedIn -> FcbRoute.DiaryFeed.value
        is MainUiState.NotSignedIn -> FcbRoute.LoginRoute.value
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(FcbTheme.colors.fcbGray),
        bottomBar = { FcbBottomNavigation(navController = navController) },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState) {
                MainSnackBar(it.visuals.message)
            }
        }
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
            startDestination = startDestination
        ) {
            loginNavGraph(
                navigateToHome = navController::navigateToDiaryFeed
            )

            diaryFeedNavGraph(
                onDiaryClick = navController::navigateToDiaryDetail,
                onNotificationClick = navController::navigateToNotification
            )

            diaryImageUploadingNavGraph(
                onImageUploadButtonClick = navController::navigateToDiaryRegistration
            )

            diaryRegistrationNavGraph(
                navController = navController,
                onDiaryRegistered = {
                    myPageViewModel.fetchMyDiaries()
                    navController.navigateToDiaryFeed()
                },
                onBackPressed = navController::popBackStack,
                onShowSnackBar = onShowSnackBar
            )

            diaryDetailNavGraph(onBackPressed = navController::popBackStack)

            myPageNavGraph(
                myPageViewModel = myPageViewModel,
                onSubscribingUserClick = navController::navigateToFollowingList,
                onSubscribedUserClick = navController::navigateToFollowerList,
                onDiaryClick = navController::navigateToDiaryDetail,
                onShowSnackBar = onShowSnackBar
            )

            followingListNavGraph(
                onUserProfileClick = navController::navigateToUserPage,
                onBackPressed = navController::popBackStack,
                onShowSnackBar = onShowSnackBar
            )

            followerListNavGraph(
                onUserProfileClick = navController::navigateToUserPage,
                onBackPressed = navController::popBackStack,
                onShowSnackBar = onShowSnackBar
            )

            notificationNavGraph(
                onProfileImgClick = navController::navigateToUserPage,
                onBackClick = navController::popBackStack,
                onShowSnackBar = onShowSnackBar
            )

            userPageNavGraph(
                onFollowingCountClick = navController::navigateToFollowingList,
                onFollowerCountClick = navController::navigateToFollowerList,
                onDiaryClick = navController::navigateToDiaryDetail,
                onBackClick = navController::popBackStack,
                onShowSnackBar = onShowSnackBar
            )

            userSearchingNavGraph(
                onUserProfileClick = navController::navigateToUserPage,
                onBackClick = navController::popBackStack,
                onShowSnackBar = onShowSnackBar
            )
        }
    }
}

@Composable
fun MainSnackBar(message: String) {
    Box(
        modifier = Modifier
            .padding(
                start = FcbTheme.padding.basicHorizontalPadding,
                end = FcbTheme.padding.basicHorizontalPadding,
                bottom = FcbTheme.padding.smallVerticalPadding01
            )
            .background(
                color = FcbTheme.colors.fcbWhite,
                shape = RoundedCornerShape(5.dp)
            )
            .fillMaxWidth()
    ) {
        Text(
            modifier = Modifier
                .padding(vertical = 6.dp)
                .fillMaxWidth(),
            text = message,
            textAlign = TextAlign.Center,
            style = FcbTheme.typography.body,
            fontSize = 14.sp
        )
    }
}

@Preview(widthDp = 360, heightDp = 640)
@Composable
fun MainPreview() {
    MainScreen()
}

@Preview(widthDp = 360, heightDp = 640)
@Composable
fun MainSnackBarPreview() {
    MainSnackBar("잘못된 요청입니다.")
}
