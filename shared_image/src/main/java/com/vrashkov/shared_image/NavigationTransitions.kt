package com.vrashkov.shared_image

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.ui.unit.Constraints
import androidx.navigation.NavBackStackEntry

@OptIn(ExperimentalAnimationApi::class)
fun exitScreen(constraints: Constraints) : (AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition?)
{
    return {
        slideOutOfContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(1500))
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun enterScreen(constraints: Constraints) : (AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition?)
{
    return {
        slideIntoContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(1500))
    }
}
@OptIn(ExperimentalAnimationApi::class)
fun exitScreenPop(constraints: Constraints) : (AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition?)
{
    return {
        slideOutOfContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(1500))
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun enterScreenPop(constraints: Constraints) : (AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition?)
{
    return {
        slideIntoContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(1500))
    }
}