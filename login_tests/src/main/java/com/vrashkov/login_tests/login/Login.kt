package com.vrashkov.login_tests.login

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.vrashkov.login_tests.base.ButtonState

@Composable
fun LoginScreenComponent(navController: NavController) {

    val viewModel: LoginVM = hiltViewModel()
    LaunchedEffect(Unit) {
        viewModel.navigationEventFlow.collect {
            when (it) {
                is LoginNavigationEvent.NavigateToHome -> {
                    navController.navigate(route = "")
                }
            }
        }
    }

    val viewState = viewModel.viewState.value
    val onTriggerEvents = viewModel::onTriggerEvent
    Column (modifier = Modifier.fillMaxSize()) {
        Box (Modifier.align(Alignment.CenterHorizontally)) {
            Column {
                TextFieldCustom(
                    value = viewState.username,
                    label = "Username",
                    onValueChanged = { newValue ->
                        onTriggerEvents(LoginEvent.OnNameChange(newValue))
                    }
                )
                Spacer(Modifier.height(10.dp))
                TextFieldCustom(
                    value = viewState.username,
                    label = "Password",
                    onValueChanged = { newValue ->
                        onTriggerEvents(LoginEvent.OnPasswordChange(newValue))
                    }
                )
                Spacer(Modifier.height(10.dp))
                Button(
                    modifier = Modifier.width(300.dp).height(48.dp),
                    enabled = viewState.continueButtonState == ButtonState.Active,
                    onClick = {
                        onTriggerEvents(LoginEvent.OnLoginClick)
                    }
                ) {
                    Text("Log In")
                }
            }
        }
    }
}

@Composable
fun TextFieldCustom(
    value: String,
    label: String,
    onValueChanged: (String) -> Unit
) {

    OutlinedTextField(
        modifier = Modifier.width(300.dp).height(48.dp),
        label = {
            Text(label)
        },
        value = value,
        onValueChange = onValueChanged,
    )
}
