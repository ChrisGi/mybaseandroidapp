package gi.aera.appsettings.domain.usecase

import gi.aera.appsettings.domain.model.UnitSettingsRepository
import gi.aera.appsettings.domain.model.UnitSystem
import gi.aera.appsettings.domain.model.UnitsSettings
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class SaveUnitSettingsUseCase internal constructor(
  private val unitSettingsRepository: UnitSettingsRepository,
  private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) {

  suspend operator fun invoke(units: UnitSystem) = withContext(dispatcher) {
    runCatching { unitSettingsRepository.saveUnits(UnitsSettings(units)) }
  }
}
