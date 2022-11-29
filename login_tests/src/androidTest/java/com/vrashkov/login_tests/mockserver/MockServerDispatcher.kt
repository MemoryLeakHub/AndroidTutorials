package com.vrashkov.login_tests

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import java.io.InputStreamReader

class MockServerDispatcher {
    fun successDispatcher(map: Map<String,String>): Dispatcher {
        return object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return when(request.path) {
                    "/auth/login" -> {
                        var json: String = ""
                        if (map.containsKey("/auth/login")) {
                            json = map.get("/auth/login")!!
                        }
                        MockResponse().setResponseCode(200).setBody(getJsonContent(json))
                    }
                    else -> MockResponse().setResponseCode(200).setBody("")
                }
            }
        }
    }
    private fun getJsonContent(fileName: String): String {
        return InputStreamReader(this.javaClass.classLoader!!.getResourceAsStream(fileName)).use { it.readText() }
    }
}