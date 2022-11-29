package com.vrashkov.login_tests.authlogin

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vrashkov.login_tests.base.DataState
import com.vrashkov.login_tests.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthVM
@Inject
constructor(
    private val loginUseCase: LoginUseCase
): ViewModel(){
    val viewState: MutableState<AuthState> = mutableStateOf(AuthState())

    val _navigationEventFlow: MutableSharedFlow<AuthNavigationEvent> = MutableSharedFlow(replay = 0)
    val navigationEventFlow: SharedFlow<AuthNavigationEvent> = _navigationEventFlow

    fun onTriggerEvent(event: AuthEvent){
        when (event) {
            is AuthEvent.OnLoginClick -> { viewModelScope.launch {
                loginUseCase.executeRemote(username = viewState.value.username, password = viewState.value.password)
                    .collect { data ->
                        when (data) {
                            is DataState.Data -> {
                                if (data.data!!) {
                                    _navigationEventFlow.emit(AuthNavigationEvent.NavigateToProfile)
                                }
                            }
                            is DataState.Error -> {}
                            is DataState.Loading -> {}
                        }
                    }
                }
            }
            is AuthEvent.OnUsernameChange -> { viewModelScope.launch {
                    viewState.value = viewState.value.copy(username = event.username)
                }
            }
            is AuthEvent.OnPasswordChange -> { viewModelScope.launch {
                    viewState.value = viewState.value.copy(password = event.password)
                }
            }
        }
    }

}


