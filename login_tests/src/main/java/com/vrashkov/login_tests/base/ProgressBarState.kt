package com.vrashkov.login_tests.base

sealed class ProgressBarState{
    
    object Loading: ProgressBarState()
    
    object Gone: ProgressBarState()
}