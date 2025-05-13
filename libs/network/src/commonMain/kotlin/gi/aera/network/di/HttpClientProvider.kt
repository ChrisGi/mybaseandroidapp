package gi.aera.network.di

import io.ktor.client.HttpClient

expect class HttpClientProvider() {

  operator fun invoke(): HttpClient
}