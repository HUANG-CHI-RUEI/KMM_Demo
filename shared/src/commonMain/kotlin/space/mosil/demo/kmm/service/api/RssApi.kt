package space.mosil.demo.kmm.service.api

import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import space.mosil.demo.kmm.models.FeedItem
import space.mosil.demo.kmm.models.RssFeeds

class RssApi {
    companion object {
        private const val RSS_ENDPOINT =
            "https://asia-east2-kotlin-serverless-api.cloudfunctions.net"
//            "https://asia-east2-kotlin-serverless-api.cloudfunctions.net/getfeed?limit=5&html=false"
    }

    private val httpClient = HttpClient {
        install(JsonFeature) {
            val json = kotlinx.serialization.json.Json {
                ignoreUnknownKeys = true
                coerceInputValues = true
            }
            serializer = KotlinxSerializer(json)
        }
    }

    suspend fun getFeeds(): RssFeeds {
        val limit = 15
        val endpoint = "$RSS_ENDPOINT/getfeed?limit=$limit"
        return httpClient.get(endpoint)
    }

    suspend fun getFeedDetail(id: String): FeedItem {
        val endpoint = "$RSS_ENDPOINT/findfeed?id=$id"
        return httpClient.get(endpoint)
    }
}