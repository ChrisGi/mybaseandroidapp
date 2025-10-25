package gi.aera.weather.forecast.domain

import gi.aera.common.dispatchers.IoDispatcher
import gi.aera.weather.forecast.domain.usecase.GetCurrentWeatherUseCase
import gi.aera.weather.forecast.domain.usecase.GetDailyForecastUseCase
import org.koin.core.qualifier.named
import org.koin.dsl.module

val weatherForecastDomainModule = module {
  single { GetDailyForecastUseCase(get(), get(), get(named(IoDispatcher))) }
  single { GetCurrentWeatherUseCase(get(), get(), get(named(IoDispatcher))) }
}
