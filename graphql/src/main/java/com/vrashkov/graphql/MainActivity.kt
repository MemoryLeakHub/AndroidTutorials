package com.vrashkov.graphql

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.vrashkov.graphql.navigation.BASE_ROUTE
import com.vrashkov.graphql.navigation.LAUNCH_ROUTE
import com.vrashkov.graphql.navigation.launchNavGraph
import com.vrashkov.graphql.ui.theme.AndroidTutorialsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
                        startDestination = LAUNCH_ROUTE,
                        route = BASE_ROUTE
                    ) {
                        launchNavGraph(
                            route = LAUNCH_ROUTE,
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}