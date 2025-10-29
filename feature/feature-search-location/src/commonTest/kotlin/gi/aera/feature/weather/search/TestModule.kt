package gi.aera.feature.weather.search

import gi.aera.common.dispatchers.IoDispatcher
import gi.aera.location.data.StubSearchLocationRepositoryImpl
import gi.aera.location.domain.locationDomainModule
import gi.aera.location.domain.repository.SearchLocationRepository
import gi.aera.location.locationDataModule
import gi.aera.weather.feature.search.searchLocationFeatureModule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.StandardTestDispatcher
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

internal val testModule = module {
  single(named(IoDispatcher)) { StandardTestDispatcher() } bind CoroutineDispatcher::class

  factory { StubSearchLocationRepositoryImpl() } bind SearchLocationRepository::class
}

internal val featureModules = module {
  includes(
    listOf(
      locationDataModule,
      locationDomainModule,
      searchLocationFeatureModule,
    ),
  )
}
