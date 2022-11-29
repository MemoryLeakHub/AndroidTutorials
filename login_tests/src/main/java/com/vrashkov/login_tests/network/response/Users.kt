package com.vrashkov.login_tests.network.response

import com.squareup.moshi.Json

data class Users(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "avatar") val avatar: UsersAvatar,
) {
    data class UsersAvatar(
        @Json(name = "thumbnail") val thumbnail: String
    )
}
