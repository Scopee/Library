package ru.pinguin.library.network.login

import retrofit2.http.POST
import retrofit2.http.Query

interface LoginApi {
    @POST("/api/v1/user/login")
    suspend fun login(@Query("username") username: String)

}