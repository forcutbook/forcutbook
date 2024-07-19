package com.fourcutbook.forcutbook.feature.diaryRegstration

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val DIARY_REGISTRATION_ROUTE = "DIARY_REGISTRATION_ROUTE"

fun NavController.navigateToDiaryRegistration(navOptions: NavOptions? = null){
    navigate(DIARY_REGISTRATION_ROUTE, navOptions)
}

fun NavGraphBuilder.diaryRegistrationNavGraph(
    onDiaryRegistry: () -> Unit
){
    composable(route = DIARY_REGISTRATION_ROUTE){
        DiaryRegistrationRoute{
            onDiaryRegistry()
        }
    }
}