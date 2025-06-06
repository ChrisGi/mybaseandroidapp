package gi.aera.network.di

import io.ktor.client.HttpClient
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.HttpTimeout
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

class HttpClientConfiguration(private val httpClientProvider: HttpClientProvider) {

  fun getHttpClient(): HttpClient = httpClientProvider.invoke().config {
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
        }
      )
    }
    defaultRequest {
      url {
        protocol = URLProtocol.HTTPS
        host = "api.tomorrow.io/v4"
        parameters.append("apikey", "l5wtsHIbpcdnao1IcRkBgnWcvXjF4AA7") //7
      }
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