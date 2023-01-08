package com.vrashkov.navigation_values

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson
import com.vrashkov.navigation_values.ui.theme.AndroidTutorialsTheme
import kotlinx.serialization.json.Json

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidTutorialsTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {

                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = PRODUCT_ROUTE,
                        route = BASE_ROUTE
                    ) {
                        productNavGraph(
                            route = PRODUCT_ROUTE,
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun ProductListScreen(navController: NavController) {

    Box(Modifier.fillMaxSize().background(Color.White)) {
        Column(modifier = Modifier.align(Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally) {
            Button(
                onClick = {
                    val product = ProductParameters(17)
                    val productLink = NavRouteName.product_details+"/"+ProductParametersType.serializeAsValue(product)
                    println("productLink : " + productLink)
                    navController.navigate(productLink)
                }
            ) {
                Text("Product List")
            }
        }
    }
}
@Composable
fun ProductDetailsScreen(navBackStackEntry: NavBackStackEntry, navController: NavController) {
    val arguments = navBackStackEntry.arguments
    val params = arguments?.getString(NavArguments.product_details_parameters)

    val paramsData = params?.let {
        ProductParametersType.parseValue(it)
    }
    Box(Modifier.fillMaxSize().background(Color.White)) {
        Column(modifier = Modifier.align(Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally) {

            Button(
                onClick = {
                    navController.navigate(Route.ProductList.link)
                }
            ) {
                val productId = paramsData?.id ?: ""
                Text("Product Details : " + productId)
            }
        }
    }
}