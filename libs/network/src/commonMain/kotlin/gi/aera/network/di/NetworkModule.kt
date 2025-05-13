package gi.aera.network.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val networkModule = module {
  singleOf(::HttpClientProvider)
}