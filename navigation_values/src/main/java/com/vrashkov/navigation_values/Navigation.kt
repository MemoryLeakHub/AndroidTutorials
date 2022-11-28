package com.vrashkov.navigation_values

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation


fun NavGraphBuilder.productNavGraph(
    route: String,
    navController: NavController,
){
    navigation(
        startDestination = Route.ProductList.link,
        route = route
    ){
        productList(navController)
        productDetails(navController)
    }
}

fun NavGraphBuilder.productList(navController: NavController) {
    composable( route = Route.ProductList.link ) {
        ProductListScreen(navController)
    }
}

fun NavGraphBuilder.productDetails(navController: NavController) {
    composable(
        route = Route.ProductDetails.link,
        arguments = Route.ProductDetails.arguments
    ) { navBackStackEntry ->
        ProductDetailsScreen(navBackStackEntry, navController)
    }
}