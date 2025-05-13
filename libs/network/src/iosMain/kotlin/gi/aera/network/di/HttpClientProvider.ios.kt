package gi.aera.network.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging

actual class HttpClientProvider {

  actual operator fun invoke() = HttpClient(Darwin) {
    install(Logging) {
      level = LogLevel.BODY
    }
    install(HttpTimeout)
    engine {
      configureRequest {
        setAllowsCellularAccess(true)
      }
    }
  }
}