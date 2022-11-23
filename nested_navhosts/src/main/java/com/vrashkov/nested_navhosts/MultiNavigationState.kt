package com.vrashkov.nested_navhosts

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun rememberMultiNavigationAppState(
    startDestination: String,
    navController: NavHostController = rememberNavController()
) = remember(navController, startDestination) {
    MultiNavigationAppState(navController, startDestination)
}

class MultiNavigationAppState(
    private var navController: NavHostController? = null,
    private val startDestination: String? = null,
) {

    fun setNavController(_navController: NavHostController) {
        navController = _navController
    }

    var getStartDestination: String = startDestination!!
        private set

    var getNavController: NavHostController = navController!!
        get() {
            return navController!!
        }
        private set

    fun setStartDestination(route: String) {
        getStartDestination = route
    }
}

data class MultiNavigationStates(
    var rootNavigation: MultiNavigationAppState = MultiNavigationAppState(),

    var baseNavigation: MultiNavigationAppState = MultiNavigationAppState(),
    var productNavigation: MultiNavigationAppState = MultiNavigationAppState(),
 )

lateinit var LocalNavigationState: MultiNavigationStates