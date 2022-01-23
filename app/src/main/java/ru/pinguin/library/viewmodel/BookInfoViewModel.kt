package ru.pinguin.library.viewmodel

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.pinguin.library.models.Book
import ru.pinguin.library.models.BookShortInfo
import ru.pinguin.library.network.analytics.AnalyticsApiService
import ru.pinguin.library.network.analytics.AnalyticsRemoteRepository
import ru.pinguin.library.network.books.BooksApiService
import ru.pinguin.library.network.books.BooksRemoteRepository

class BookInfoViewModel(private val isbn: String, private val activity: Activity) : ViewModel() {

    private val remoteRepository: BooksRemoteRepository
    private val mutableLiveData: MutableLiveData<Book> = MutableLiveData()
    var liveData: LiveData<Book> = mutableLiveData

    init {
        val apiService = BooksApiService.service
        remoteRepository = BooksRemoteRepository(apiService)
        load(isbn) {}
    }

    fun load(isbn: String, callback: () -> Unit) {
        this.viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = remoteRepository.getBookInfo(isbn)
                mutableLiveData.postValue(response)
                callback()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}