package com.vrashkov.login_tests.login

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vrashkov.login_tests.base.ButtonState
import com.vrashkov.login_tests.base.DataState
import com.vrashkov.login_tests.usecase.LoginBase64UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginVM @Inject constructor(
    private val loginBase64UseCase: LoginBase64UseCase
) : ViewModel() {

    var viewState: MutableState<LoginState> = mutableStateOf(LoginState())

    protected val _navigationEventFlow: MutableSharedFlow<LoginNavigationEvent> = MutableSharedFlow(replay = 0)
    val navigationEventFlow: SharedFlow<LoginNavigationEvent> = _navigationEventFlow

    fun onTriggerEvent(event: LoginEvent) {
        when (event) {
            LoginEvent.OnLoginClick -> {
                viewModelScope.launch {
                    loginBase64UseCase.execute(
                        viewState.value.username,
                        viewState.value.password
                    ).collect { result ->
                        when (result) {
                            is DataState.Loading -> {
                                viewState.value = viewState.value
                                    .copy(continueButtonState = ButtonState.Loading)
                            }
                            is DataState.Data -> {
                                _navigationEventFlow.emit(LoginNavigationEvent.NavigateToHome)
                            }
                            else -> {}
                        }
                    }
                }
            }
            is LoginEvent.OnNameChange -> {
                viewState.value = viewState.value
                    .copy(username = event.newValue)
            }
            is LoginEvent.OnPasswordChange -> {
                viewState.value = viewState.value
                    .copy(password = event.newValue)
            }
        }
    }
}