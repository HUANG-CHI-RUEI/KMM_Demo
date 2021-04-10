package space.mosil.demo.kmm.android

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import space.mosil.demo.kmm.GdgLogger
import space.mosil.demo.kmm.models.FeedItem
import space.mosil.demo.kmm.service.RssService

class RssDetailViewModel : ViewModel() {
    private val TAG = RssDetailViewModel::class.simpleName ?: ""

    private val feedDetail = MutableLiveData<FeedItem>()
    val detail: LiveData<FeedItem>
        get() = feedDetail

    private val service = RssService()

    fun getDetail(id: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                service.getFeedDetail(id)
            }.onSuccess {
                feedDetail.value = it
                GdgLogger.i(TAG, it.toString())
            }.onFailure {
                GdgLogger.e(TAG, it.toString())
            }
        }
    }
}