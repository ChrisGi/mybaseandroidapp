package gi.aera.network.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging

actual class HttpClientProvider actual constructor() {

  actual operator fun invoke() = HttpClient(OkHttp) {
    install(Logging) {
      level = LogLevel.BODY
    }
    install(HttpTimeout)
  }
}