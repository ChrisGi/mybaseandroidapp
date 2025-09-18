package gi.aera.location

import gi.aera.location.data.DefaultLocationRepository
import gi.aera.location.data.LocationApi
import gi.aera.location.data.SaveLocationRepository
import gi.aera.location.data.SearchLocationRepository
import gi.aera.location.domain.usecase.GetDefaultLocationUseCase
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
import org.koin.dsl.module

val locationModule = module {
  includes(platformModule)

  single { LocationApi(get(named(LOCATION_HTTP_CLIENT))) }
  singleOf(::SearchLocationRepository)
  single { SearchLocationUseCase(get()) }

  singleOf(::SaveLocationRepository)
  single { SaveLocationUseCase(get()) }
  single { GetSavedLocationUseCase(get()) }
  single { RemoveSavedLocationUseCase(get(), get()) }

  singleOf(::DefaultLocationRepository)
  single { SaveDefaultLocationUseCase(get()) }
  single { GetDefaultLocationUseCase(get(), get()) }
  single { RemoveDefaultLocationUseCase(get()) }
}

expect val platformModule: Module
