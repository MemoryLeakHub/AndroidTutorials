package com.vrashkov.login_tests

import app.cash.turbine.test
import app.cash.turbine.testIn
import com.vrashkov.login_tests.base.ButtonState
import com.vrashkov.login_tests.base.DataState
import com.vrashkov.login_tests.login.LoginEvent
import com.vrashkov.login_tests.login.LoginNavigationEvent
import com.vrashkov.login_tests.login.LoginState
import com.vrashkov.login_tests.login.LoginVM
import com.vrashkov.login_tests.usecase.LoginBase64UseCase
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule

class LoginVMTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @MockK
    lateinit var loginBase64UseCase: LoginBase64UseCase

    private lateinit var viewModel: LoginVM

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    private fun produceViewModel(viewState: LoginState = LoginState()): LoginVM {
        val viewModel = LoginVM(loginBase64UseCase = loginBase64UseCase)
        viewModel.viewState.value = viewState
        return viewModel
    }

    private fun getCurrentViewState() = viewModel.viewState.value

    @Test
    fun `when OnNameChange is triggered with non-empty value`() {
        val fakeValue = "123"
        viewModel = produceViewModel(
            viewState = LoginState()
        )

        viewModel.onTriggerEvent(LoginEvent.OnNameChange(newValue = fakeValue))

        assertEquals(fakeValue, getCurrentViewState().username)
    }

    @Test
    fun `when OnPasswordChange is triggered with non-empty value`() {
        val fakeValue = "123"
        viewModel = produceViewModel(
            viewState = LoginState()
        )

        viewModel.onTriggerEvent(LoginEvent.OnPasswordChange(newValue = fakeValue))

        assertEquals(fakeValue, getCurrentViewState().password)
    }

    @Test
    fun `when OnLoginClick is triggered with user and pass`() = runTest {
        val fakeUsername = "user"
        val fakePassword = "pass"

        val emailSlot = slot<String>()
        val passwordSlot = slot<String>()
        coEvery {
            loginBase64UseCase.execute(capture(emailSlot), capture(passwordSlot))
        } returns flowOf()

        viewModel = produceViewModel(
            viewState = LoginState(
                username = fakeUsername,
                password = fakePassword
            )
        )

        viewModel.onTriggerEvent(LoginEvent.OnLoginClick)

        coVerify { loginBase64UseCase.execute(any(), any()) }

        val email = emailSlot.captured
        val pass = passwordSlot.captured
        assertEquals(fakeUsername, email)
        assertEquals(fakePassword, pass)
    }

    @Test
    fun `when OnLoginClick is triggered and loginInteractor emits Loading`() = runTest {

        coEvery {
            loginBase64UseCase.execute(any(), any())
        } returns flowOf(DataState.Loading())

        viewModel = produceViewModel(
            viewState = LoginState()
        )

        viewModel.onTriggerEvent(LoginEvent.OnLoginClick)

        assertEquals(ButtonState.Loading, getCurrentViewState().continueButtonState)
    }

    @Test
    fun `when OnLoginClick is triggered and loginInteractor emits Data`() = runTest {

        coEvery {
            loginBase64UseCase.execute(any(), any())
        } returns flowOf(DataState.Data())

        viewModel = produceViewModel(
            viewState = LoginState()
        )

        backgroundScope.launch {
            viewModel.onTriggerEvent(LoginEvent.OnLoginClick)
        }

        viewModel.navigationEventFlow.test {

            val navigationEvent = awaitItem()
            assertTrue(navigationEvent is LoginNavigationEvent.NavigateToHome)

            cancelAndConsumeRemainingEvents()
        }
    }
}