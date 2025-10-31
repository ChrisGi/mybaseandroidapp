package gi.aera.feature.weather.location

import gi.aera.appsettings.data.StubGetUnitSettingsRepositoryImpl
import gi.aera.appsettings.domain.appSettingsDomainModule
import gi.aera.appsettings.domain.repository.UnitSettingsRepository
import gi.aera.common.dispatchers.IoDispatcher
import gi.aera.feature.weather.doubles.StubLocationRepositoryImpl
import gi.aera.location.domain.locationDomainModule
import gi.aera.location.domain.repository.LocationRepository
import gi.aera.location.locationDataModule
import gi.aera.weather.feature.location.weatherLocationsFeatureModule
import gi.aera.weather.forecast.data.StubForecastRepositoryImpl
import gi.aera.weather.forecast.domain.repository.ForecastRepository
import gi.aera.weather.forecast.domain.weatherForecastDomainModule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.StandardTestDispatcher
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

internal val testModule = module {
  single(named(IoDispatcher)) { StandardTestDispatcher() } bind CoroutineDispatcher::class

  factory { StubLocationRepositoryImpl() } bind LocationRepository::class
  factory { StubForecastRepositoryImpl() } bind ForecastRepository::class
  factory { StubGetUnitSettingsRepositoryImpl() } bind UnitSettingsRepository::class
}

internal val featureModules = module {
  includes(
    listOf(
      locationDataModule,
      locationDomainModule,
      appSettingsDomainModule,
      weatherForecastDomainModule,
      weatherLocationsFeatureModule,
    ),
  )
}
