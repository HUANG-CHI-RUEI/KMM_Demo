package space.mosil.demo.kmm.android

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import space.mosil.demo.kmm.models.FeedItem

class RssFeedsAdapter(var feeds: List<FeedItem>) :
    RecyclerView.Adapter<RssFeedsAdapter.RssFeedViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RssFeedViewHolder {
        return LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_rss, parent, false)
            .run(::RssFeedViewHolder)
    }

    override fun onBindViewHolder(holder: RssFeedViewHolder, position: Int) {
        holder.bindView(feeds[position])
    }

    override fun getItemCount(): Int = feeds.size

    inner class RssFeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView = itemView.findViewById<TextView>(R.id.txt_item_rss_title)
        private val releaseTextView =
            itemView.findViewById<TextView>(R.id.txt_item_rss_publish)

        fun bindView(item: FeedItem) {
            val context = itemView.context

            titleTextView.text = item.title
            releaseTextView.text = item.publishedAt
        }
    }
}