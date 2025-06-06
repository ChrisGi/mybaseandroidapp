package gi.aera.network.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp

actual class HttpClientProvider actual constructor() {

  actual operator fun invoke() = HttpClient(OkHttp)
}