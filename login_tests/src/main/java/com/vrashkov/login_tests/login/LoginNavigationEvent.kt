package com.vrashkov.login_tests.login

sealed class LoginNavigationEvent() {
    object NavigateToHome: LoginNavigationEvent()
}