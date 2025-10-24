package gi.aera.appsettings.domain

import gi.aera.appsettings.domain.usecase.GetUnitSettingsUseCase
import gi.aera.appsettings.domain.usecase.SaveUnitSettingsUseCase
import gi.aera.common.dispatchers.IoDispatcher
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appSettingsDomainModule = module {
  single { SaveUnitSettingsUseCase(get(), get(named(IoDispatcher))) }
  single { GetUnitSettingsUseCase(get(), get(named(IoDispatcher))) }
}
