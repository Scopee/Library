package ru.pinguin.library.network.analytics

import ru.pinguin.library.models.BookShortInfo
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface AnalyticsApi {

    @GET("/api/v1/library/books")
    suspend fun getBooksList(): List<BookShortInfo>

    @POST("/api/v1/library/book/{isbn}/rate")
    suspend fun rateBook(@Path("isbn") isbn: String, @Query("username") username: String, @Query("rate") rate: Int)
}