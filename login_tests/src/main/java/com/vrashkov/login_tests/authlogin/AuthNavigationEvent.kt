package com.vrashkov.login_tests.authlogin

sealed class AuthNavigationEvent {
    object NavigateToProfile: AuthNavigationEvent()
}