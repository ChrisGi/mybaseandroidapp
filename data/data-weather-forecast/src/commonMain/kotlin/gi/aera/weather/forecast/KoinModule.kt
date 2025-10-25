package gi.aera.weather.forecast

import gi.aera.network.WEATHER_HTTP_CLIENT
import gi.aera.weather.forecast.data.ForecastRepositoryImpl
import gi.aera.weather.forecast.domain.repository.ForecastRepository
import gi.aera.weather.forecast.domain.weatherForecastDomainModule
import gi.aera.weather.forecast.source.ForecastApi
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val weatherForecastDataModule = module {
  single { ForecastApi(get(named(WEATHER_HTTP_CLIENT))) }
  singleOf(::ForecastRepositoryImpl) bind ForecastRepository::class

  includes(weatherForecastDomainModule)
}
