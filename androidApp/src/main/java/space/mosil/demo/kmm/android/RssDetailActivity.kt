package space.mosil.demo.kmm.android

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.coil.CoilImage
import space.mosil.demo.kmm.GdgLogger
import space.mosil.demo.kmm.models.FeedItem

class RssDetailActivity : AppCompatActivity() {
    companion object {
        const val FEED_ID = "feed_id"

        fun getIntent(context: Context, feedId: String): Intent {
            val intent = Intent(context, RssDetailActivity::class.java)
            intent.putExtra(FEED_ID, feedId);
            GdgLogger.d("getIntent", "Feed Id: ${feedId}")
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val feedId = intent.getStringExtra(FEED_ID)
        GdgLogger.d("onCreate", "Feed Id: ${feedId}")
        setContent {
            if (feedId != null) {
                getDetail(id = feedId)
            }
        }
    }

}

@Composable
fun getDetail(id: String) {
    val vm: RssDetailViewModel = viewModel()
    val detailState: State<FeedItem?> = vm.detail.observeAsState()

    vm.getDetail(id)
    detailState.let {
        it.value?.let { detail ->
            SetDetail(detail = detail)
        }
    }
}

@Composable
fun SetDetail(detail: FeedItem) {

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center  //垂直滑動
    ) {
        Text(detail.title, fontWeight = FontWeight.Bold)
        Text(text = detail.author)
        Text(detail.publishedAt)
        if (detail.coverUrl != null) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(bottom = 8.dp)
            ) {
                CoilImage(
                    data = detail.coverUrl!!,
                    contentDescription = detail.title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Inside
                )
            }
        }
        getWebView(content = detail.content)
    }
}

@Composable
fun getWebView(content: String) {
    val context = LocalContext.current

    AndroidView(
        factory = {
            WebView(it).apply {
                zoomIn()
                loadDataWithBaseURL(null, content, "text/html", "utf-8", null)
            }
        }
    )
}