package com.vrashkov.shared_image

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.unit.Constraints
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.navigation
import com.google.accompanist.navigation.animation.composable


@ExperimentalAnimationApi
@ExperimentalMaterialApi
fun NavGraphBuilder.productNavGraph(
    route: String,
    constraints: Constraints,
    navController: NavController,
){
    navigation(
        startDestination = Route.ProductList.link,
        route = route
    ){
        productList(constraints, navController)
        productDetails(constraints, navController)
    }
}

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialApi::class)
fun NavGraphBuilder.productList(constraints: Constraints, navController: NavController) {
    composable( route = Route.ProductList.link,
        exitTransition = exitScreen(constraints),
        enterTransition = enterScreen(constraints),
        popExitTransition = exitScreenPop(constraints),
        popEnterTransition = enterScreenPop(constraints),
    ) {
        ProductListScreen(navController)
    }
}

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialApi::class)
fun NavGraphBuilder.productDetails(constraints: Constraints, navController: NavController) {
    composable( route = Route.ProductDetails.link,
        exitTransition = exitScreen(constraints),
        enterTransition = enterScreen(constraints),
        popExitTransition = exitScreenPop(constraints),
        popEnterTransition = enterScreenPop(constraints),
    ) {
        ProductDetailsScreen(navController)
    }
}