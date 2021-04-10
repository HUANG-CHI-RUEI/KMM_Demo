package space.mosil.demo.kmm.android

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.coil.CoilImage
import com.google.accompanist.imageloading.ImageLoadState
import com.google.accompanist.imageloading.MaterialLoadingImage
import space.mosil.demo.kmm.models.FeedItem

class RssComposeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GetRssFeeds()
        }
    }
}

@Composable
fun GetRssFeeds() {
    val vm: RssComposeViewModel = viewModel()
    val rssFeeds: State<List<FeedItem>?> = vm.feedList.observeAsState()
    vm.getFeeds()
    rssFeeds.let {
        it.value?.let { list ->
            SetItemList(list = list)
        }
    }
}

@Composable
fun SetItemList(list: List<FeedItem>) {
    LazyColumn {
        items(list) { item ->
            FeedItemCard(item = item)
        }
    }
}

@Composable
fun FeedItemCard(
    item: FeedItem,
) {
    val context = LocalContext.current

    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                context.startActivity(RssDetailActivity.getIntent(context = context, item.id))
            },
        backgroundColor = MaterialTheme.colors.surface,
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center
        ) {
            if (item.coverUrl != null) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .padding(bottom = 8.dp)
                ) {
                    CoilImage(
                        data = item.coverUrl!!,
                        contentDescription = item.title,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Inside
                    )
                }
            }
            Text(text = item.title, modifier = Modifier.padding(4.dp, 4.dp, 8.dp, 4.dp))
            Text(text = item.publishedAt)
        }
    }
}