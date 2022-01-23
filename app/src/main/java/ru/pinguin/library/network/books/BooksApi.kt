package ru.pinguin.library.network.books

import retrofit2.http.*
import ru.pinguin.library.models.Book
import ru.pinguin.library.models.RateInfo
import ru.pinguin.library.models.UpdateBookRequest

interface BooksApi {

    @GET("/api/v1/library/book/{isbn}")
    suspend fun getBookInfo(@Path("isbn") isbn: String): Book

    @POST("/api/v1/library/book/")
    suspend fun createBook(@Body book: Book)

    @PUT("/api/v1/library/book/{isbn}")
    suspend fun updateBook(@Path("isbn") isbn: String, @Body updateBookRequest: UpdateBookRequest)

    @POST("/api/v1/library/book/isbn/{isbn}")
    suspend fun createBookByIsbn(@Path("isbn") isbn: String): Book

    @GET("/api/v1/library/book/rated")
    suspend fun isRated(@Query("username") username: String, @Query("isbn") isbn: String): RateInfo
}