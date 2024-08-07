package com.fourcutbook.forcutbook.feature

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.fourcutbook.forcutbook.design.FcbTheme

@Composable
fun FcbBottomNavigation(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    // ㄹtodo : 언제 보여지고 언제 사라질지

    if (currentRoute == FcbRoute.LoginRoute.value) return

    BottomNavigation(
        modifier = modifier.fillMaxWidth().wrapContentHeight(),
        backgroundColor = FcbTheme.colors.fcbWhite,
        contentColor = FcbTheme.colors.fcbDarkBeige
    ) {
        FcbBottomNavigationItem.entries.forEach { item ->
            BottomNavigationItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let {
                            popUpTo(it) { saveState = true }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        modifier = Modifier.size(FcbTheme.shame.iconSize),
                        painter = painterResource(id = item.icon),
                        contentDescription = null
                    )
                },
                selectedContentColor = FcbTheme.colors.fcbDarkBeige02,
                unselectedContentColor = FcbTheme.colors.fcbLightBeige02
            )
        }
    }
}

@Preview
@Composable
fun FcbBottomNavigationPreview() {
    FcbBottomNavigation(navController = rememberNavController())
}
