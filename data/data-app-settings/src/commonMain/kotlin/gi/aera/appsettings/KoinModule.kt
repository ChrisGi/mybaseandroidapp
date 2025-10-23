package gi.aera.appsettings

import gi.aera.appsettings.data.UnitSettingsRepositoryImpl
import gi.aera.appsettings.domain.model.UnitSettingsRepository
import gi.aera.appsettings.domain.usecase.GetUnitSettingsUseCase
import gi.aera.appsettings.domain.usecase.SaveUnitSettingsUseCase
import gi.aera.common.dispatchers.IoDispatcher
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val appSettingsDataModule = module {
  singleOf(::UnitSettingsRepositoryImpl) bind UnitSettingsRepository::class
  single { SaveUnitSettingsUseCase(get(), get(named(IoDispatcher))) }
  single { GetUnitSettingsUseCase(get(), get(named(IoDispatcher))) }
}
