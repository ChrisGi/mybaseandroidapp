package gi.aera.location.domain

import gi.aera.common.dispatchers.IoDispatcher
import gi.aera.location.domain.usecase.GetDefaultLocationUseCase
import gi.aera.location.domain.usecase.GetLastGpsLocationUseCase
import gi.aera.location.domain.usecase.GetSavedLocationUseCase
import gi.aera.location.domain.usecase.RemoveDefaultLocationUseCase
import gi.aera.location.domain.usecase.RemoveSavedLocationUseCase
import gi.aera.location.domain.usecase.SaveDefaultLocationUseCase
import gi.aera.location.domain.usecase.SaveLocationUseCase
import gi.aera.location.domain.usecase.SearchLocationUseCase
import org.koin.core.qualifier.named
import org.koin.dsl.module

val locationDomainModule = module {
  single { SearchLocationUseCase(get(), get(named(IoDispatcher))) }

  single { SaveLocationUseCase(get(), get(named(IoDispatcher))) }
  single { GetSavedLocationUseCase(get(), get(named(IoDispatcher))) }
  single { RemoveSavedLocationUseCase(get(), get(), get(named(IoDispatcher))) }

  single { SaveDefaultLocationUseCase(get(), get(named(IoDispatcher))) }
  single { GetDefaultLocationUseCase(get(), get(), get(named(IoDispatcher))) }
  single { RemoveDefaultLocationUseCase(get(), get(named(IoDispatcher))) }

  factory { GetLastGpsLocationUseCase(get(), get(named(IoDispatcher))) }
}
