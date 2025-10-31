package weather.feature.forecast

import gi.aera.appsettings.appSettingsDataModule
import gi.aera.appsettings.data.StubGetUnitSettingsRepositoryImpl
import gi.aera.appsettings.domain.repository.UnitSettingsRepository
import gi.aera.common.dispatchers.IoDispatcher
import gi.aera.location.locationDataModule
import gi.aera.ui.navigation.navigationModule
import gi.aera.weather.feature.forecast.weatherForecastFeatureModule
import gi.aera.weather.forecast.domain.repository.ForecastRepository
import gi.aera.weather.forecast.data.StubForecastRepositoryImpl
import gi.aera.weather.forecast.weatherForecastDataModule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.StandardTestDispatcher
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

internal val testModule = module {
  factory { StubForecastRepositoryImpl() } bind ForecastRepository::class
  factory { StubGetUnitSettingsRepositoryImpl() } bind UnitSettingsRepository::class

  single(named(IoDispatcher)) { StandardTestDispatcher() } bind CoroutineDispatcher::class
}

internal val featureModules = module {
  includes(
    listOf(
      locationDataModule,
      navigationModule,
      appSettingsDataModule,
      weatherForecastDataModule,
      weatherForecastFeatureModule,
    ),
  )
}
