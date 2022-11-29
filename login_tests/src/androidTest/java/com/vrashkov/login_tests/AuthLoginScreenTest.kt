package com.vrashkov.login_tests

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.espresso.IdlingRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jakewharton.espresso.OkHttp3IdlingResource
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase.assertEquals
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@HiltAndroidTest
class AuthLoginScreenTest {

    @get:Rule(order=0)
    var hiltRule = HiltAndroidRule(this)
    @get:Rule(order=1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Inject lateinit var okHttp: OkHttpClient
    lateinit var mockServer: MockWebServer
    val serviceMap: Map<String, String> = mapOf(
        Pair("/auth/login", "auth_login_success.json")
    )
    lateinit var idlingResource: OkHttp3IdlingResource
    @After
    fun stopService() {
        mockServer.shutdown()
        IdlingRegistry.getInstance().unregister(idlingResource)
    }

    @Before
    fun setup() {
        hiltRule.inject()

        idlingResource = OkHttp3IdlingResource.create("okhttp", okHttp)
        mockServer = MockWebServer()
        mockServer.start(8080)

        IdlingRegistry.getInstance().register(idlingResource)
    }


    @Test
    fun test_authentication_login_success() {

        val email = "test@mail.com"
        val password = "password"

        mockServer.dispatcher = MockServerDispatcher().successDispatcher(serviceMap)

        composeTestRule.onNodeWithContentDescription("enter email")
            .assertIsDisplayed()
            .performTextInput(email)


        composeTestRule.onNodeWithContentDescription("enter password")
            .assertIsDisplayed()
            .performTextInput(password)


        composeTestRule.onNodeWithContentDescription("login click")
            .assertIsDisplayed()
            .performClick()

        val request: RecordedRequest = mockServer.takeRequest()
        assertEquals("/auth/login", request.path)

        composeTestRule
            .onNodeWithContentDescription("logged in")
            .assertIsDisplayed()

    }
}