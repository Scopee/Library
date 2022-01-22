package ru.pinguin.library.models

import com.google.gson.annotations.SerializedName

data class Book(
    @SerializedName("isbn") var isbn: String,
    @SerializedName("title") var title: String,
    @SerializedName("authors") var authors: String,
    @SerializedName("description") var description: String,
    @SerializedName("year") var year: Int
)
