package com.vrashkov.login_tests.authlogin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.vrashkov.login_tests.NavRouteName

@Composable
fun AuthLoginScreen(navController: NavController) {

    val viewModel = hiltViewModel<AuthVM>()

    val onTriggerEvents = viewModel::onTriggerEvent
    val viewState = viewModel.viewState.value

    LaunchedEffect(Unit) {
        viewModel.navigationEventFlow.collect {
            when (it) {
                is AuthNavigationEvent.NavigateToProfile -> {
                    navController.navigate(NavRouteName.auth_profile)
                }
            }
        }
    }

    AuthLogin(viewModel, viewState, onTriggerEvents)
}

@Composable
fun AuthLogin(
    viewModel: AuthVM,
    viewState: AuthState,
    onTriggerEvents: (AuthEvent) -> Unit,
) {
    Box(Modifier.fillMaxSize()) {
        Column(modifier = Modifier.align(Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally) {

            OutlinedTextField(
                modifier = Modifier.semantics {
                    contentDescription = "enter email"
                },
                value = viewState.username,
                placeholder = {
                  Text("Username")
                },
                onValueChange = {
                    onTriggerEvents(AuthEvent.OnUsernameChange(it))
                }
            )
            OutlinedTextField(
                modifier = Modifier.semantics {
                    contentDescription = "enter password"
                },
                value = viewState.password,
                placeholder = {
                    Text("Password")
                },
                onValueChange = {
                    onTriggerEvents(AuthEvent.OnPasswordChange(it))
                }
            )
            Button(
                modifier = Modifier.semantics {
                    contentDescription = "login click"
                },
                onClick = {
                    onTriggerEvents(AuthEvent.OnLoginClick)
                }
            ) {
                Text("Login")
            }
        }
    }
}