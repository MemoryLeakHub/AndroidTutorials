package com.vrashkov.login_tests

import app.cash.turbine.test
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Test
import android.util.Base64
import app.cash.turbine.testIn
import com.vrashkov.login_tests.base.DataState
import com.vrashkov.login_tests.base.RequestResult
import com.vrashkov.login_tests.network.mapped.LoginResult
import com.vrashkov.login_tests.repository.GeneralRepository
import com.vrashkov.login_tests.usecase.LoginBase64UseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.test.*
import org.junit.Assert.*

class HandleIdentityUseCaseTest {

    @MockK
    private lateinit var generalRepository: GeneralRepository

    private lateinit var useCase: LoginBase64UseCase

    private val loginSuccessResult = LoginResult("apikey")
    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    private fun produceUseCase() = LoginBase64UseCase(
        generalRepository = generalRepository
    )

    @Before
    fun `Bypass android_util_Base64 to java_util_Base64`() {
        mockkStatic(Base64::class)
        val arraySlot = slot<ByteArray>()

        every {
            Base64.encodeToString(capture(arraySlot), Base64.DEFAULT)
        } answers {
            java.util.Base64.getEncoder().encodeToString(arraySlot.captured)
        }

        val stringSlot = slot<String>()
        every {
            Base64.decode(capture(stringSlot), Base64.DEFAULT)
        } answers {
            java.util.Base64.getDecoder().decode(stringSlot.captured)
        }
    }

    @Test
    fun `execute login check if credentials was null`() = runTest {
        val email = null
        val password = null
        coEvery {
            generalRepository.login(null)
        } returns RequestResult.Success(loginSuccessResult)

        useCase = produceUseCase()

        useCase.execute(email, password).testIn(backgroundScope)

        coVerify { generalRepository.login(isNull()) }
    }

    @Test
    fun `execute login check if we have passed credentials`() = runTest  {
        val email = "email"
        val password = "pass"
        val credentials = "credentials"
        coEvery {
            generalRepository.login(any())
        } returns RequestResult.Success(loginSuccessResult)

        useCase = produceUseCase()

        every {
            useCase.getCredentials(email,password)
        } returns credentials

        useCase.execute(email, password).testIn(backgroundScope)

        coVerify { generalRepository.login(isNull(inverse = true)) }
    }
    @Test
    fun `execute login without credentials and return data`() = runTest {
        val email = null
        val password = null
        coEvery {
            generalRepository.login(null)
        } returns RequestResult.Success(LoginResult(apiKey = "test"))
        useCase = produceUseCase()

        useCase.execute(email, password).test {
            val firstResult = awaitItem()
            assertTrue(firstResult is DataState.Loading)

            val secondResult = awaitItem()
            assertTrue(secondResult is DataState.Data)

            awaitComplete()
        }
    }

    @Test
    fun `execute login without credentials and return error`() = runTest {
        val email = null
        val password = null
        val fakeException = Exception()
        coEvery {
            generalRepository.login(null)
        } returns RequestResult.Error(fakeException)

        useCase = produceUseCase()

        val flow = useCase.execute(email, password)

        flow.test {
            val firstResult = awaitItem()
            assertTrue(firstResult is DataState.Loading)

            val secondResult = awaitItem()
            assertTrue(secondResult is DataState.Error)
            assertEquals(fakeException, (secondResult as DataState.Error).error)
            awaitComplete()
        }
    }

}