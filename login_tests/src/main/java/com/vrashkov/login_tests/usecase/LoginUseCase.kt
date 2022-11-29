package com.vrashkov.login_tests.usecase

import android.widget.ProgressBar
import com.vrashkov.login_tests.base.DataState
import com.vrashkov.login_tests.base.ProgressBarState
import com.vrashkov.login_tests.network.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class LoginUseCase @Inject constructor(
    private val apiService: ApiService
) {
     suspend fun execute(username: String, password: String): Flow<DataState<Boolean>> = flow {
        emit(DataState.Loading(progressState = ProgressBarState.Loading))

        if (username.isNotEmpty() && password.isNotEmpty()) {
            emit(DataState.Data(true))
        } else {
            emit(DataState.Data(false))
        }
    }

    suspend fun executeRemote(username: String, password: String): Flow<DataState<Boolean>> = flow {
        emit(DataState.Loading(progressState = ProgressBarState.Loading))

        if (username.isNotEmpty() && password.isNotEmpty()) {
            try{
                val network = apiService.login()

                val networkResponse = network.body()!!

                emit(DataState.Data(networkResponse.success))
            } catch (e: Exception) {
                emit(DataState.Error(Exception("Something went wrong!")))
            }
        } else {
            emit(DataState.Data(false))
        }
    }
}
