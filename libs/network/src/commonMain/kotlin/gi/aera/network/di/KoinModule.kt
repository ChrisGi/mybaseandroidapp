package gi.aera.network.di

import io.ktor.client.HttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.module

val networkModule = module {
  single<HttpClient>(named(WEATHER_HTTP_CLIENT)) {
    WeatherApiHttpClientConfiguration(HttpClientProvider()).getHttpClient()
  }
  single<HttpClient>(named(LOCATION_HTTP_CLIENT)) {
    LocationApiHttpClientConfiguration(HttpClientProvider()).getHttpClient()
  }
}

const val WEATHER_HTTP_CLIENT = "WEATHER_HTTP_CLIENT"
const val LOCATION_HTTP_CLIENT = "LOCATION_HTTP_CLIENT"
