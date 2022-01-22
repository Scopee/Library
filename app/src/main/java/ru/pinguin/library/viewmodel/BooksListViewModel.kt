package ru.pinguin.library.viewmodel

import android.app.Activity
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.pinguin.library.MainActivity
import ru.pinguin.library.models.BookShortInfo
import ru.pinguin.library.network.analytics.AnalyticsApi
import ru.pinguin.library.network.analytics.AnalyticsApiService
import ru.pinguin.library.network.analytics.AnalyticsRemoteRepository

class BooksListViewModel(val activity: Activity) : ViewModel() {

    private val remoteRepository: AnalyticsRemoteRepository
    private val mutableLiveData: MutableLiveData<List<BookShortInfo>> = MutableLiveData()
    var liveData: LiveData<List<BookShortInfo>> = mutableLiveData

    init {
        val apiService = AnalyticsApiService.service
        remoteRepository = AnalyticsRemoteRepository(apiService)
        load {}
    }

    fun load(callback: () -> Unit) {
        this.viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = remoteRepository.getBooksList()
                mutableLiveData.postValue(response)
                callback()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}