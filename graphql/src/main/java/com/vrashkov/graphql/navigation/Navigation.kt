package com.vrashkov.graphql.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.vrashkov.graphql.ui.list.LaunchListScreen

fun NavGraphBuilder.launchNavGraph(
    route: String,
    navController: NavController,
){
    navigation(
        startDestination = Route.LaunchList.link,
        route = route
    ){
        launchList(navController)
    }
}

fun NavGraphBuilder.launchList(navController: NavController) {
    composable( route = Route.LaunchList.link ) {
        LaunchListScreen(navController)
    }
}