package ru.pinguin.library.models

import com.google.gson.annotations.SerializedName

data class BookShortInfo(
    @SerializedName("isbn") val isbn: String,
    @SerializedName("title") val title: String,
    @SerializedName("rate") val rate: Double
)
