package ru.pinguin.library.network.analytics

import retrofit2.http.*
import ru.pinguin.library.models.BookShortInfo

interface AnalyticsApi {

    @GET("/api/v1/analytics/books")
    @Headers("Content-Type: application/json")
    suspend fun getBooksList(): List<BookShortInfo>

    @POST("/api/v1/analytics/book/{isbn}/rate")
    @Headers("Content-Type: application/json")
    suspend fun rateBook(@Path("isbn") isbn: String, @Query("username") username: String, @Query("rate") rate: Int)
}