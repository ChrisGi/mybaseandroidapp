package gi.aera.network

import gi.aera.network.config.LocationApiHttpClientConfiguration
import gi.aera.network.config.WeatherApiHttpClientConfiguration
import io.ktor.client.HttpClient
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

val networkModule = module {
  includes(platformModule)

  single<HttpClient>(named(WEATHER_HTTP_CLIENT)) {
    WeatherApiHttpClientConfiguration(get()).getHttpClient()
  }
  single<HttpClient>(named(LOCATION_HTTP_CLIENT)) {
    LocationApiHttpClientConfiguration(get()).getHttpClient()
  }
}

expect val platformModule: Module

const val WEATHER_HTTP_CLIENT = "WEATHER_HTTP_CLIENT"
const val LOCATION_HTTP_CLIENT = "LOCATION_HTTP_CLIENT"
