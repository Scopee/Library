package ru.pinguin.library.models

data class UpdateBookRequest(var title: String, var authors: String, var description: String, var year: Int)