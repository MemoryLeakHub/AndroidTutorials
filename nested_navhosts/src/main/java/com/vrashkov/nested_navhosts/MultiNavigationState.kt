package com.vrashkov.nested_navhosts

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
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

    fun printBackStack() {
        println("--------------------")
        navController!!.currentBackStack.value.forEach {
            println("screen : ${it.destination.route}")
        }
        println("--------------------")
    }
    @Composable
    fun isRouteActive(route: String): Boolean {
        var navHostController = navController

        return if (navHostController != null) {
            val destination = navHostController.getDestination()
            return destination?.any {
                (it.route.equals(route))
            } ?: false
        } else {
            false
        }
    }
    fun navigateTo(route: String, popUpTo: Boolean, popUpToInclusive: Boolean) {
        getNavController.navigate(route) {
            if (popUpTo) {
                popUpTo(route) {
                    inclusive = popUpToInclusive
                    saveState = false
                }
            }
        }
    }
}
@Composable
fun NavHostController.getDestination() : Sequence<NavDestination>? {
    val navBackStackEntry by this.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    return currentDestination?.hierarchy
}
data class MultiNavigationStates(
    var rootNavigation: MultiNavigationAppState = MultiNavigationAppState(),

    var baseNavigation: MultiNavigationAppState = MultiNavigationAppState(),
    var productNavigation: MultiNavigationAppState = MultiNavigationAppState(),
 )

lateinit var LocalNavigationState: MultiNavigationStates