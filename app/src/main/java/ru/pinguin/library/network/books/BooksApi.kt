package ru.pinguin.library.network.books

import retrofit2.http.*
import ru.pinguin.library.models.Book
import ru.pinguin.library.models.RateInfo
import ru.pinguin.library.models.UpdateBookRequest

interface BooksApi {

    @GET("/api/v1/library/book/{isbn}")
    @Headers("Content-Type: application/json")
    suspend fun getBookInfo(@Path("isbn") isbn: String): Book

    @POST("/api/v1/library/book/")
    @Headers("Content-Type: application/json")
    suspend fun createBook(@Body book: Book)

    @PUT("/api/v1/library/book/{isbn}")
    @Headers("Content-Type: application/json")
    suspend fun updateBook(@Path("isbn") isbn: String, @Body updateBookRequest: UpdateBookRequest)

    @POST("/api/v1/library/book/isbn/{isbn}")
    @Headers("Content-Type: application/json")
    suspend fun createBookByIsbn(@Path("isbn") isbn: String): Book

    @GET("/api/v1/library/book/rated")
    @Headers("Content-Type: application/json")
    suspend fun isRated(@Query("username") username: String, @Query("isbn") isbn: String): RateInfo
}