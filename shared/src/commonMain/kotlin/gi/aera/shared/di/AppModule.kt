package gi.aera.shared.di

import gi.aera.appsettings.appSettingsDataModule
import gi.aera.feature.settings.appSettingsFeatureModule
import gi.aera.location.locationModule
import gi.aera.network.di.networkModule
import gi.aera.prefrences.preferencesModule
import gi.aera.weather.feature.forecast.weatherForecastFeatureModule
import gi.aera.weather.feature.location.weatherLocationsFeatureModule
import gi.aera.weather.feature.search.searchLocationFeatureModule
import gi.aera.weather.forecast.weatherForecastDataModule

val appModules = listOf(
  networkModule,
  locationModule,
  preferencesModule,

  weatherForecastDataModule,
  appSettingsDataModule,

  weatherForecastFeatureModule,
  weatherLocationsFeatureModule,
  searchLocationFeatureModule,
  appSettingsFeatureModule,
)
