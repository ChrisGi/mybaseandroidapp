package gi.aera.location

import gi.aera.common.dispatchers.IoDispatcher
import gi.aera.location.data.DefaultLocationRepositoryImpl
import gi.aera.location.data.LocationApi
import gi.aera.location.data.SaveLocationRepositoryImpl
import gi.aera.location.data.SearchLocationRepositoryImpl
import gi.aera.location.domain.model.DefaultLocationRepository
import gi.aera.location.domain.model.SaveLocationRepository
import gi.aera.location.domain.model.SearchLocationRepository
import gi.aera.location.domain.usecase.GetDefaultLocationUseCase
import gi.aera.location.domain.usecase.GetLastGpsLocationUseCase
import gi.aera.location.domain.usecase.GetSavedLocationUseCase
import gi.aera.location.domain.usecase.RemoveDefaultLocationUseCase
import gi.aera.location.domain.usecase.RemoveSavedLocationUseCase
import gi.aera.location.domain.usecase.SaveDefaultLocationUseCase
import gi.aera.location.domain.usecase.SaveLocationUseCase
import gi.aera.location.domain.usecase.SearchLocationUseCase
import gi.aera.network.LOCATION_HTTP_CLIENT
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val locationModule = module {
  includes(platformModule)

  single { LocationApi(get(named(LOCATION_HTTP_CLIENT))) }
  singleOf(::SearchLocationRepositoryImpl) bind SearchLocationRepository::class
  single { SearchLocationUseCase(get(), get(named(IoDispatcher))) }

  singleOf(::SaveLocationRepositoryImpl) bind SaveLocationRepository::class
  single { SaveLocationUseCase(get(), get(named(IoDispatcher))) }
  single { GetSavedLocationUseCase(get(), get(named(IoDispatcher))) }
  single { RemoveSavedLocationUseCase(get(), get(), get(named(IoDispatcher))) }

  singleOf(::DefaultLocationRepositoryImpl) bind DefaultLocationRepository::class
  single { SaveDefaultLocationUseCase(get(), get(named(IoDispatcher))) }
  single { GetDefaultLocationUseCase(get(), get(), get(named(IoDispatcher))) }
  single { RemoveDefaultLocationUseCase(get(), get(named(IoDispatcher))) }

  factory { GetLastGpsLocationUseCase(get(), get(named(IoDispatcher))) }
}

expect val platformModule: Module
