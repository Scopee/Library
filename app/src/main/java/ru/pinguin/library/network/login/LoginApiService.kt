package ru.pinguin.library.network.login

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.pinguin.library.network.books.BooksApi

object LoginApiService {

    val service: LoginApi

    private val BASE_URL = "http://130.193.36.98"

    init {
        val gson = GsonBuilder()
            .create()
        val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }).build()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
        service = retrofit.create(LoginApi::class.java)
    }
}