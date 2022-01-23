package ru.pinguin.library.models

import com.google.gson.annotations.SerializedName

data class RateInfo(@SerializedName("rated") var rated: Boolean, @SerializedName("rate") var rate: Int)
