package gi.aera.appsettings.domain.usecase

import gi.aera.appsettings.domain.model.UnitSettingsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class GetUnitSettingsUseCase internal constructor(
  private val unitSettingsRepository: UnitSettingsRepository,
  private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) {

  operator fun invoke() = unitSettingsRepository.getUnits()
    .map { it.unit }
    .flowOn(dispatcher)
}
