package com.fourcutbook.forcutbook.feature.main

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.forcutbook.forcutbook.R
import com.fourcutbook.forcutbook.feature.diaryRegistration.diaryRegistrationNavGraph
import com.fourcutbook.forcutbook.feature.diaryRegistration.navigateToDiaryRegistration
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

    Scaffold(
        modifier = Modifier,
        topBar = { HomeForCutBookLogo() },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            Box {
                FloatingActionButton(
                    onClick = { navController.navigateToImageUploading() },
                    shape = CircleShape,
                    containerColor = Color(0xFF1DA1F2),
                    modifier = Modifier
                        .offset(y = 50.dp)
                        .align(Alignment.Center)
                        .size(60.dp)
                ) {
                    Icon(
                        modifier = Modifier
                            .size(30.dp),
                        painter = painterResource(id = R.drawable.ic_register_diary),
                        tint = Color.White,
                        contentDescription = null
                    )
                }
            }
        },
        bottomBar = {
            MainBottomAppBar(navController = navController)
        }
    ) { padding ->
        NavHost(
            modifier = Modifier
                .background(color = Color(0xFFEDEDED))
                .padding(top = 50.dp),
            navController = navController,
            startDestination = LOGIN_ROUTE
        ) {
            Log.d("woogi", "MainScreen: asdas")
            loginNavGraph(navController::navigateToHome)

            homeNavGraph(
                navigateToDiaryRegistration = navController::navigateToImageUploading,
                navigateToDiaryDetails = {}
            )

            imageUploadingNavGraph { navController.navigateToDiaryRegistration() }

            diaryRegistrationNavGraph(
                navController = navController,
                navigateToHomeScreen = navController::navigateToHome,
                onShowSnackBar = onShowSnackBar
            )
        }
        padding
    }
}

@Composable
fun HomeForCutBookLogo(modifier: Modifier = Modifier) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp, start = 30.dp, end = 30.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            textAlign = TextAlign.Center,
            modifier = modifier
                .wrapContentWidth(),
            text = "포컷북",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                modifier = modifier
                    .wrapContentWidth()
                    .height(28.dp),
                painter = painterResource(id = R.drawable.ic_alarm),
                contentDescription = null
            )
        }
    }
}

@Composable
fun MainBottomAppBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    BottomAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(65.dp),
        containerColor = Color.White
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(
                modifier = Modifier.padding(start = 30.dp),
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    tint = Color(0xFF545454),
                    modifier = Modifier.size(45.dp),
                    imageVector = Icons.Default.Search,
                    contentDescription = null
                )
            }
            IconButton(
                modifier = Modifier.padding(end = 30.dp),
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    tint = Color(0xFF545454),
                    modifier = Modifier.size(45.dp),
                    painter = painterResource(id = R.drawable.ic_my_page),
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
