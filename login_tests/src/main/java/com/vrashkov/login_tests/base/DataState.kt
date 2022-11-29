package com.vrashkov.login_tests.base

sealed class DataState<T> {

    data class Data<T>(
        val data: T? = null
    ): DataState<T>()

    data class Loading<T>(
        val progressState: ProgressBarState = ProgressBarState.Gone
    ): DataState<T>()

    data class Error<T>(
        val error: Exception? = null
    ): DataState<T>()
}
