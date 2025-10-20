package gi.aera.network.config

import gi.aera.lib.network.ApiKeys
import gi.aera.network.cache.ForceCachePluginInstaller
import io.ktor.client.HttpClient
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.plugins.resources.Resources
import io.ktor.client.statement.bodyAsText
import io.ktor.http.URLProtocol
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

internal class WeatherApiHttpClientConfiguration(
  private val httpClient: HttpClient,
) {

  fun getHttpClient(): HttpClient = httpClient.config {
    ForceCachePluginInstaller(maxAge = 60)
    install(HttpCache)
    install(Resources)
    install(Logging) {
      logger = Logger.SIMPLE
      level = LogLevel.ALL
    }
    install(HttpTimeout)
    install(ContentNegotiation) {
      json(
        Json {
          ignoreUnknownKeys = true
        },
      )
    }
    defaultRequest {
      url {
        protocol = URLProtocol.HTTPS
        host = "api.tomorrow.io/v4"
      }
      headers.append("apikey", ApiKeys.tomorrowApiKey)
    }

    expectSuccess = false
    HttpResponseValidator {
      validateResponse { response ->
        if (!response.status.isSuccess()) {
          throw ClientRequestException(
            response = response,
            cachedResponseText = response.bodyAsText(),
          )
        }
      }
    }
  }
}
