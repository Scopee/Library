package ru.pinguin.library.network.analytics

import ru.pinguin.library.models.BookShortInfo
import ru.pinguin.library.utils.retry

class AnalyticsRemoteRepository(private val analyticsApi: AnalyticsApi) {

    suspend fun getBooksList(): List<BookShortInfo> = retry(times = 3) {
        analyticsApi.getBooksList()
    }

    suspend fun rateBook(isbn: String, username: String, rate: Int) = retry(times = 3) { analyticsApi.rateBook(isbn, username, rate) }

}