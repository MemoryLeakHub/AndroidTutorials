package com.vrashkov.login_tests.network.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.serialization.Serializable

@Serializable
@JsonClass(generateAdapter = true)
data class AuthLoginResponse(
    @Json(name="success")
    val success: Boolean
)