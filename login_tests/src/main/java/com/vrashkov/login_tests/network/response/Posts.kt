package com.vrashkov.login_tests.network.response

import com.squareup.moshi.Json

data class Posts(
    @Json(name = "userId") val userId: Int,
    @Json(name = "id") val id: Int,
    @Json(name = "title") val title: String,
    @Json(name = "body") val body: String,
)
