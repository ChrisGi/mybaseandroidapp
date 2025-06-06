package gi.aera.shared.di

import gi.aera.network.di.networkModule
import gi.aera.weather.feature.forecast.weatherForecastFeatureModule
import gi.aera.weather.forecast.weatherForecastDataModule

val appModules = listOf(
  networkModule,

  weatherForecastDataModule,
  weatherForecastFeatureModule
)