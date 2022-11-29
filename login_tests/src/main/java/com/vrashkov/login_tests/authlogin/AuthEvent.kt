package com.vrashkov.login_tests.authlogin

sealed class AuthEvent {
    object OnLoginClick: AuthEvent()
    data class OnUsernameChange(val username: String): AuthEvent()
    data class OnPasswordChange(val password: String): AuthEvent()
}