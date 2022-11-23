package com.vrashkov.nested_navhosts

import android.graphics.Paint.Align
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vrashkov.nested_navhosts.ui.theme.AndroidTutorialsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidTutorialsTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    ContentWrapper()
                }
            }
        }
    }
}
@Composable
fun ContentWrapper() {
    LocalNavigationState = MultiNavigationStates(
        rootNavigation = rememberMultiNavigationAppState(startDestination = ROOT_ROUTE),

        baseNavigation = rememberMultiNavigationAppState(startDestination = Route.Base_tab_1.link),
        productNavigation = rememberMultiNavigationAppState(startDestination = Route.ProductList.link)
    )
    NavHost(
        navController = LocalNavigationState.rootNavigation.getNavController,
        startDestination = BASE_ROUTE,
        route = ROOT_ROUTE
    ) {
        composable(
            route = BASE_ROUTE,
        ) {
            LocalNavigationState.baseNavigation.setNavController(rememberNavController())
            Dashboard {
                NavHost(
                    navController = LocalNavigationState.baseNavigation.getNavController,
                    startDestination = BASE_ROUTE,
                    route = ROOT_ROUTE
                ) {
                    baseTabNavGraph(
                        appState = LocalNavigationState.baseNavigation,
                        route = BASE_ROUTE,
                    )
                }
            }
        }
        composable(
            route = PRODUCT_ROUTE,
        ) {
            LocalNavigationState.productNavigation.setNavController(rememberNavController())
            Box() {
                NavHost(
                    navController = LocalNavigationState.productNavigation.getNavController,
                    startDestination = PRODUCT_ROUTE,
                    route = ROOT_ROUTE
                ) {
                    productNavGraph(
                        appState = LocalNavigationState.productNavigation,
                        route = PRODUCT_ROUTE,
                    )
                }
            }
        }
    }
}
@Composable
fun ProductListScreen() {
    Box(Modifier.fillMaxSize().background(Color.Red)) {
        Text(modifier = Modifier.align(Alignment.Center), text = "ProductListScreen")
    }
}

@Composable
fun ProductDetailsScreen() {
    Box(Modifier.fillMaxSize().background(Color.Blue)) {
        Text(modifier = Modifier.align(Alignment.Center), text = "ProductDetailsScreen")
    }
}

@Composable
fun Tab1Screen() {
    Box(Modifier.fillMaxSize().background(Color.Yellow)) {
        Column(modifier = Modifier.align(Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally) {
            Button(
                onClick = {
                    LocalNavigationState.productNavigation.setStartDestination(route = Route.ProductList.link)
                    LocalNavigationState.rootNavigation.getNavController.navigate(route = PRODUCT_ROUTE)
                }
            ) {
                Text("Product List")
            }
            Spacer(Modifier.height(20.dp))
            Button(
                onClick = {
                    LocalNavigationState.productNavigation.setStartDestination(route = Route.ProductDetails.link)
                    LocalNavigationState.rootNavigation.getNavController.navigate(route = PRODUCT_ROUTE)
                }
            ) {
                Text("Product Details")
            }
        }
    }
}

@Composable
fun Tab2Screen() {
    Box(Modifier.fillMaxSize().background(Color.Green)) {
        Text(modifier = Modifier.align(Alignment.Center), text = "Tab2Screen")
    }
}

@Composable
fun Tab3Screen() {
    Box(Modifier.fillMaxSize().background(Color.Cyan)) {
        Text(modifier = Modifier.align(Alignment.Center), text = "Tab3Screen")
    }
}