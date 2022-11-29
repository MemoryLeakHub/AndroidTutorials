package com.vrashkov.login_tests

import androidx.navigation.*

const val BASE_ROUTE = "base"
const val AUTH_ROUTE = "auth"

object NavRouteName {
    const val auth_login = "auth_login"
    const val auth_profile = "user_profile"
}
sealed class Route(val link: String, val arguments: List<NamedNavArgument> = emptyList()) {
    object AuthLogin: Route(link = "${NavRouteName.auth_login}")
    object AuthProfile: Route(link = "${NavRouteName.auth_profile}")
}