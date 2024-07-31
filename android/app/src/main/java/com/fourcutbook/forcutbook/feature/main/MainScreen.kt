package com.fourcutbook.forcutbook.feature.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.fourcutbook.forcutbook.feature.diaryDetail.diaryDetailNavGraph
import com.fourcutbook.forcutbook.feature.diaryDetail.navigateToDiaryDetail
import com.fourcutbook.forcutbook.feature.diaryRegistration.diaryRegistrationNavGraph
import com.fourcutbook.forcutbook.feature.diaryRegistration.navigateToDiaryRegistration
import com.fourcutbook.forcutbook.feature.home.HOME_ROUTE
import com.fourcutbook.forcutbook.feature.home.homeNavGraph
import com.fourcutbook.forcutbook.feature.home.navigateToHome
import com.fourcutbook.forcutbook.feature.imageUploading.imageUploadingNavGraph
import com.fourcutbook.forcutbook.feature.imageUploading.navigateToImageUploading
import com.fourcutbook.forcutbook.feature.login.LOGIN_ROUTE
import com.fourcutbook.forcutbook.feature.login.loginNavGraph
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
        modifier = Modifier,
        topBar = { FcbTopAppBar(currentRoute = currentRoute) },
        bottomBar = { FcbBottomNavigation(navController = navController) }
    ) { padding ->
        NavHost(
            modifier = Modifier
                .background(color = FcbTheme.colors.fcbGray)
                .padding(top = FcbTheme.padding.basicVerticalPadding),
            navController = navController,
            startDestination = LOGIN_ROUTE
        ) {
            loginNavGraph { navController.navigateToHome() }

            homeNavGraph(
                navigateToDiaryRegistration = navController::navigateToImageUploading,
                navigateToDiaryDetails = navController::navigateToDiaryDetail
            )

            imageUploadingNavGraph { navController.navigateToDiaryRegistration() }

            diaryRegistrationNavGraph(
                navController = navController,
                navigateToHomeScreen = navController::navigateToHome,
                onShowSnackBar = onShowSnackBar
            )

            diaryDetailNavGraph(
                navController = navController,
                onShowSnackBar = onShowSnackBar
            )
        }
        padding
    }
}

@Composable
fun FcbTopAppBar(
    modifier: Modifier = Modifier,
    currentRoute: String?
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = FcbTheme.padding.smallVerticalPadding,
                start = FcbTheme.padding.basicHorizontalPadding,
                end = FcbTheme.padding.basicHorizontalPadding
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            textAlign = TextAlign.Center,
            modifier = modifier
                .wrapContentWidth(),
            style = FcbTheme.typography.heading,
            text = currentRoute ?: stringResource(R.string.string_header_of_home_screen)
        )

        if (currentRoute == HOME_ROUTE) {
            IconButton(onClick = { /*TODO*/ }) {
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
}

@Preview(widthDp = 360, heightDp = 640)
@Composable
fun MainPreview() {
    MainScreen()
}
