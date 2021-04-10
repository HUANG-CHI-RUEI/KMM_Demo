package space.mosil.demo.kmm.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FeedItem(
    @SerialName("id")
    val id: String,
    @SerialName("title")
    val title: String,
    @SerialName("author")
    val author: String,
    @SerialName("content")
    val content: String,
    @SerialName("coverUrl")
    val coverUrl: String?,
    @SerialName("permalink")
    val permalink: String,
    @SerialName("publishedAt")
    val publishedAt: String
)

@Serializable
data class RssFeeds(
    @SerialName("data")
    val list: List<FeedItem>
)