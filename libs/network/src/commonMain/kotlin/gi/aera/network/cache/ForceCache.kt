package gi.aera.network.cache

import io.ktor.client.HttpClientConfig
import io.ktor.client.call.HttpClientCall
import io.ktor.client.statement.HttpReceivePipeline
import io.ktor.client.statement.HttpResponse
import io.ktor.http.Headers
import io.ktor.http.HeadersBuilder
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpProtocolVersion
import io.ktor.http.HttpStatusCode
import io.ktor.util.date.GMTDate
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.InternalAPI
import kotlin.coroutines.CoroutineContext

fun HttpClientConfig<*>.ForceCachePluginInstaller(maxAge: Int = 10) {
  install("ForceCache") {
    receivePipeline.intercept(HttpReceivePipeline.Before) { response ->
      proceedWith(
        object : HttpResponse() {
          override val call: HttpClientCall = response.call
          override val coroutineContext: CoroutineContext = response.coroutineContext
          override val requestTime: GMTDate = response.requestTime
          override val responseTime: GMTDate = response.responseTime
          @InternalAPI override val rawContent: ByteReadChannel = response.rawContent
          override val status: HttpStatusCode = response.status
          override val version: HttpProtocolVersion = response.version
          override val headers: Headers = HeadersBuilder().apply {
            appendAll(response.headers)
            remove(HttpHeaders.CacheControl)
            append(HttpHeaders.CacheControl, "max-age=$maxAge")
          }.build()
        },
      )
    }
  }
}
