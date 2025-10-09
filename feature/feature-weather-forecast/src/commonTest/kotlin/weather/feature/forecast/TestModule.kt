package weather.feature.forecast

import gi.aera.appsettings.appSettingsDataModule
import gi.aera.appsettings.data.FakeGetUnitSettingsRepositoryImpl
import gi.aera.appsettings.domain.model.UnitSettingsRepository
import gi.aera.weather.feature.forecast.weatherForecastFeatureModule
import gi.aera.weather.forecast.data.FakeForecastRepositoryImpl
import gi.aera.weather.forecast.data.ForecastRepository
import gi.aera.weather.forecast.weatherForecastDataModule
import org.koin.dsl.bind
import org.koin.dsl.module

internal val testModule = module {
  factory { FakeForecastRepositoryImpl() } bind ForecastRepository::class
  factory { FakeGetUnitSettingsRepositoryImpl() } bind UnitSettingsRepository::class
}

internal val featureModules = module {
  includes(
    listOf(
      appSettingsDataModule,
      weatherForecastDataModule,
      weatherForecastFeatureModule,
    ),
  )
}
