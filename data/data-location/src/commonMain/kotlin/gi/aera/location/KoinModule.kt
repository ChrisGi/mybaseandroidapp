package gi.aera.location

import gi.aera.location.data.DefaultLocationRepositoryImpl
import gi.aera.location.data.LocationRepositoryImpl
import gi.aera.location.data.SearchLocationRepositoryImpl
import gi.aera.location.source.LocationApi
import gi.aera.location.domain.locationDomainModule
import gi.aera.location.domain.repository.DefaultLocationRepository
import gi.aera.location.domain.repository.LocationRepository
import gi.aera.location.domain.repository.SearchLocationRepository
import gi.aera.network.LOCATION_HTTP_CLIENT
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val locationDataModule = module {
  includes(platformModule)

  single { LocationApi(get(named(LOCATION_HTTP_CLIENT))) }
  singleOf(::SearchLocationRepositoryImpl) bind SearchLocationRepository::class

  singleOf(::LocationRepositoryImpl) bind LocationRepository::class

  singleOf(::DefaultLocationRepositoryImpl) bind DefaultLocationRepository::class

  includes(locationDomainModule)
}

expect val platformModule: Module
