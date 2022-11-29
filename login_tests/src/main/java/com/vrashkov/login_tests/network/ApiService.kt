package com.vrashkov.login_tests.network

import android.accounts.Account
import com.vrashkov.login_tests.network.response.AuthLoginResponse
import com.vrashkov.login_tests.network.response.Posts
import com.vrashkov.login_tests.network.response.Users
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @GET("/auth/login")
    suspend fun login(): Response<AuthLoginResponse>

    @GET("login")
    suspend fun login(@Header("Authorization") credentials: String?): Account

    @GET("posts")
    suspend fun posts(): List<Posts>

    @GET("users")
    suspend fun users(): List<Users>
}