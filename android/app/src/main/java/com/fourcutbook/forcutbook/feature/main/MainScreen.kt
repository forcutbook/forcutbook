package com.fourcutbook.forcutbook.feature.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.forcutbook.forcutbook.R
import com.fourcutbook.forcutbook.design.FcbTheme
import com.fourcutbook.forcutbook.feature.FcbBottomNavigation
import com.fourcutbook.forcutbook.feature.FcbRoute
import com.fourcutbook.forcutbook.feature.diaryfeed.diaryDetail.diaryDetailNavGraph
import com.fourcutbook.forcutbook.feature.diaryfeed.diaryDetail.navigateToDiaryDetail
import com.fourcutbook.forcutbook.feature.diaryfeed.diaryFeedNavGraph
import com.fourcutbook.forcutbook.feature.diaryfeed.navigateToDiaryFeed
import com.fourcutbook.forcutbook.feature.diaryposting.diaryImageUploading.diaryImageUploadingNavGraph
import com.fourcutbook.forcutbook.feature.diaryposting.diaryImageUploading.navigateToDiaryImageUploading
import com.fourcutbook.forcutbook.feature.diaryposting.diaryRegistration.diaryRegistrationNavGraph
import com.fourcutbook.forcutbook.feature.diaryposting.diaryRegistration.navigateToDiaryRegistration
import com.fourcutbook.forcutbook.feature.login.navigation.loginNavGraph
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
        modifier = Modifier.fillMaxSize(),
        topBar = { FcbTopAppBar(currentRoute = FcbRoute.find(currentRoute)) },
        bottomBar = { FcbBottomNavigation(navController = navController) }
    ) { contentPadding ->
        NavHost(
            modifier = Modifier
                .background(color = FcbTheme.colors.fcbGray)
                .padding(
                    top = contentPadding.calculateTopPadding(),
                    bottom = contentPadding.calculateBottomPadding(),
                    start = FcbTheme.padding.basicHorizontalPadding,
                    end = FcbTheme.padding.basicVerticalPadding
                )
                .fillMaxSize(),
            navController = navController,
            startDestination = FcbRoute.LoginRoute.value
        ) {
            loginNavGraph(navigateToHome = navController::navigateToDiaryFeed)

            diaryFeedNavGraph(
                navigateToDiaryRegistration = navController::navigateToDiaryImageUploading,
                navigateToDiaryDetails = navController::navigateToDiaryDetail
            )

            diaryImageUploadingNavGraph(navigateToDiaryScreen = navController::navigateToDiaryRegistration)

            diaryRegistrationNavGraph(
                navController = navController,
                navigateToHomeScreen = navController::navigateToDiaryFeed,
                onBackPressed = navController::popBackStack,
                onShowSnackBar = onShowSnackBar
            )

            diaryDetailNavGraph(
                navController = navController,
                navigateToDiaryFeed = navController::navigateToDiaryFeed,
                onBackPressed = navController::popBackStack,
                onShowSnackBar = onShowSnackBar
            )
        }
    }
}

@Composable
fun FcbTopAppBar(
    modifier: Modifier = Modifier,
    currentRoute: FcbRoute?
) {
    if (currentRoute == null) return
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = FcbTheme.padding.basicVerticalPadding,
                start = FcbTheme.padding.basicHorizontalPadding,
                end = FcbTheme.padding.basicHorizontalPadding
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Text(
            textAlign = TextAlign.Center,
            modifier = modifier.wrapContentWidth(),
            style = FcbTheme.typography.heading,
            text = currentRoute
                .header
                ?.let { rid -> stringResource(id = rid) }
                ?: stringResource(id = R.string.string_header_of_home_screen)
        )

        if (currentRoute is FcbRoute.DiaryFeed) {
            Icon(
                modifier = modifier
                    .wrapContentWidth()
                    .height(FcbTheme.shame.iconSize),
                painter = painterResource(id = R.drawable.ic_alarm),
                contentDescription = null
            )
        }
    }
}

@Preview(widthDp = 360, heightDp = 640)
@Composable
fun MainPreview() {
    MainScreen()
}
