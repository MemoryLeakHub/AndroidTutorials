package com.vrashkov.login_tests.login

import com.vrashkov.login_tests.base.ButtonState

data class LoginState (
    val username: String = "",
    val password: String = "",
    val continueButtonState: ButtonState = ButtonState.Active
)