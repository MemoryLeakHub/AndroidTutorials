package com.vrashkov.graphql.navigation

import androidx.navigation.*

const val BASE_ROUTE = "base"
const val LAUNCH_ROUTE = "launch"

object NavRouteName {
    const val launch_list = "launch_list"
}

sealed class Route(val link: String, val arguments: List<NamedNavArgument> = emptyList()) {
    object LaunchList: Route(link = "${NavRouteName.launch_list}")
}