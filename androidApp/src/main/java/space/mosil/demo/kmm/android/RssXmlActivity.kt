package space.mosil.demo.kmm.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import space.mosil.demo.kmm.GdgLogger
import space.mosil.demo.kmm.service.RssService

class RssXmlActivity : AppCompatActivity() {
    private val TAG = RssXmlActivity::class.simpleName ?: ""

    private lateinit var rssList: RecyclerView
    private val rssAdapter = RssFeedsAdapter(listOf())

    private val mainScope = MainScope()
    private val service = RssService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "GDG Demo App XML Layout"
        setContentView(R.layout.activity_xml_layout)

        rssList = findViewById(R.id.rlv_xml_list)
        rssList.adapter = rssAdapter
        rssList.layoutManager = LinearLayoutManager(this)

        getRssFeeds()
    }

    override fun onDestroy() {
        super.onDestroy()
        mainScope.cancel()
    }

    private fun getRssFeeds() {
        mainScope.launch {
            kotlin.runCatching {
                service.getFeeds()
            }.onSuccess {
                rssAdapter.feeds = it.list
                rssAdapter.notifyDataSetChanged()
            }.onFailure {
                GdgLogger.e(TAG, it.toString())
            }
        }
    }
}