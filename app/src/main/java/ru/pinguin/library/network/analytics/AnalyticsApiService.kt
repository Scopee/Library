package ru.pinguin.library.network.analytics

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AnalyticsApiService {
    val service: AnalyticsApi

    private val BASE_URL = "http://10.0.2.2:8081"

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
        service = retrofit.create(AnalyticsApi::class.java)
    }
}