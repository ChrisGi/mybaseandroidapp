package gi.aera.weather.forecast

import gi.aera.network.di.WEATHER_HTTP_CLIENT
import gi.aera.weather.forecast.data.ForecastApi
import gi.aera.weather.forecast.data.ForecastRepository
import gi.aera.weather.forecast.data.ForecastRepositoryImpl
import gi.aera.weather.forecast.domain.usecase.GetCurrentWeatherUseCase
import gi.aera.weather.forecast.domain.usecase.GetDailyForecastUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val weatherForecastDataModule = module {
  single { ForecastApi(get(named(WEATHER_HTTP_CLIENT))) }
  singleOf(::ForecastRepositoryImpl) bind ForecastRepository::class
  single { GetDailyForecastUseCase(get(), get()) }
  single { GetCurrentWeatherUseCase(get(), get()) }
}
