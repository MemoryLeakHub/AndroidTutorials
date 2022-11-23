package com.vrashkov.nested_navhosts

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation


fun NavGraphBuilder.productNavGraph(
    route: String,
    appState: MultiNavigationAppState,
){
    navigation(
        startDestination = appState.getStartDestination,
        route = route
    ){
        productList()
        productDetails()
    }
}

fun NavGraphBuilder.productList() {
    composable( route = Route.ProductList.link) {
        ProductListScreen()
    }
}

fun NavGraphBuilder.productDetails() {
    composable( route = Route.ProductDetails.link) {
        ProductDetailsScreen()
    }
}


fun NavGraphBuilder.baseTabNavGraph(
    route: String,
    appState: MultiNavigationAppState,
){
    navigation(
        startDestination = appState.getStartDestination,
        route = route
    ){
        tab1()
        tab2()
        tab3()
    }
}

fun NavGraphBuilder.tab1() {
    composable( route = Route.Base_tab_1.link) {
        Tab1Screen()
    }
}

fun NavGraphBuilder.tab2() {
    composable( route = Route.Base_tab_2.link) {
        Tab2Screen()
    }
}
fun NavGraphBuilder.tab3() {
    composable( route = Route.Base_tab_3.link) {
        Tab3Screen()
    }
}