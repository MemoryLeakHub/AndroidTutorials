package com.vrashkov.login_tests

import app.cash.turbine.test
import com.vrashkov.login_tests.base.DataState
import com.vrashkov.login_tests.base.RequestResult
import com.vrashkov.login_tests.network.mapped.PostResult
import com.vrashkov.login_tests.network.mapped.UserResult
import com.vrashkov.login_tests.repository.GeneralRepository
import com.vrashkov.login_tests.usecase.ListUserPostsUseCase
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

class ListUserPostsUseCaseTest {

    @MockK
    private lateinit var generalRepository: GeneralRepository

    private lateinit var useCase: ListUserPostsUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    private fun produceUseCase() = ListUserPostsUseCase(
        generalRepository = generalRepository
    )

    @Test
    fun `execute users and return error`() = runTest {

        val fakeException = Exception()
        coEvery {
            generalRepository.users()
        } returns RequestResult.Error(fakeException)

        useCase = produceUseCase()

        val flow = useCase.execute()

        flow.test {
            val firstResult = awaitItem()
            assertTrue(firstResult is DataState.Loading)

            val secondResult = awaitItem()
            assertTrue(secondResult is DataState.Error)
            assertEquals(fakeException, (secondResult as DataState.Error).error)
            awaitComplete()
        }

    }

    @Test
    fun `execute users success and posts error`() = runTest {

        val fakeException = Exception()
        val fakeUsersData: List<UserResult> = listOf()

        coEvery {
            generalRepository.users()
        } returns RequestResult.Success(data = fakeUsersData)

        coEvery {
            generalRepository.posts()
        } returns RequestResult.Error(fakeException)

        useCase = produceUseCase()

        val flow = useCase.execute()

        flow.test {
            val firstResult = awaitItem()
            assertTrue(firstResult is DataState.Loading)

            val secondResult = awaitItem()
            assertTrue(secondResult is DataState.Error)
            assertEquals(fakeException, (secondResult as DataState.Error).error)
            awaitComplete()
        }
    }


    @Test
    fun `execute users success and posts success`() = runTest {

        val fakeUsersData: List<UserResult> = listOf()
        val fakePostsData: List<PostResult> = listOf()

        coEvery {
            generalRepository.users()
        } returns RequestResult.Success(data = fakeUsersData)

        coEvery {
            generalRepository.posts()
        } returns RequestResult.Success(data = fakePostsData)

        useCase = produceUseCase()

        val flow = useCase.execute()

        flow.test {
            val firstResult = awaitItem()
            assertTrue(firstResult is DataState.Loading)

            val secondResult = awaitItem()
            assertTrue(secondResult is DataState.Data)
            awaitComplete()
        }
    }
}