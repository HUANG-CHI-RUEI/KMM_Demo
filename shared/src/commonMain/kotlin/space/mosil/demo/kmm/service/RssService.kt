package space.mosil.demo.kmm.service

import space.mosil.demo.kmm.GdgLogger
import space.mosil.demo.kmm.models.FeedItem
import space.mosil.demo.kmm.models.RssFeeds
import space.mosil.demo.kmm.service.api.RssApi

class RssService {
    companion object {
        internal val TAG = RssService::class.simpleName ?: ""
    }

    private val api = RssApi()

    suspend fun getFeeds(): RssFeeds {
        api.getFeeds().also {
            GdgLogger.i(TAG, it.toString())
            return it
        }
    }

    suspend fun getFeedDetail(id: String): FeedItem {
        api.getFeedDetail(id).also {
            GdgLogger.i(TAG, it.toString())
            return it
        }
    }
}