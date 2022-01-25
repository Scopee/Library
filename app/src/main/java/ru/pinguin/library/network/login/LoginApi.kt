package ru.pinguin.library.network.login

import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface LoginApi {
    @POST("/api/v1/user/login")
    @Headers("Content-Type: application/json")
    suspend fun login(@Query("username") username: String)

}