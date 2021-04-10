package space.mosil.demo.kmm.android

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import space.mosil.demo.kmm.GdgLogger
import space.mosil.demo.kmm.models.FeedItem
import space.mosil.demo.kmm.service.RssService

class RssComposeViewModel : ViewModel() {
    private val TAG = RssComposeViewModel::class.simpleName ?: ""

    private val rssFeeds = MutableLiveData<List<FeedItem>>()
    val feedList: LiveData<List<FeedItem>>
        get() = rssFeeds

    private val service = RssService()

    fun getFeeds() {
        viewModelScope.launch {
            kotlin.runCatching {
                service.getFeeds()
            }.onSuccess {
                rssFeeds.value = it.list
                GdgLogger.i(TAG, it.toString())
            }.onFailure {
                GdgLogger.e(TAG, it.toString())
            }
        }
    }

}