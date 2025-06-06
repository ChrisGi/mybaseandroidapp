package gi.aera.network.di

import org.koin.dsl.module

val networkModule = module {
  single { HttpClientConfiguration(HttpClientProvider()).getHttpClient() }
}