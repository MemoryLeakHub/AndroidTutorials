package com.vrashkov.login_tests

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.vrashkov.login_tests.authlogin.AuthLoginScreen


fun NavGraphBuilder.authGraph(
    route: String,
    navController: NavController,
){
    navigation(
        startDestination = Route.AuthLogin.link,
        route = route
    ){
        login(navController)
        profile(navController)
    }
}

fun NavGraphBuilder.login(navController: NavController) {
    composable( route = Route.AuthLogin.link ) {
        AuthLoginScreen(navController)
    }
}

fun NavGraphBuilder.profile(navController: NavController) {
    composable(
        route = Route.AuthProfile.link,
    ) {
        AuthProfileScreen(navController)
    }
}