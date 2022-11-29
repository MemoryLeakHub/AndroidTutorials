package com.vrashkov.login_tests.base

sealed class ButtonState {

    object Active: ButtonState()

    object Disabled: ButtonState()

    object Loading: ButtonState()

}