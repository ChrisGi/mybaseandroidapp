package gi.aera.appsettings.data

import gi.aera.appsettings.domain.model.UnitSettingsRepository
import gi.aera.appsettings.domain.model.UnitSystem
import gi.aera.appsettings.domain.model.UnitsSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeGetUnitSettingsRepositoryImpl : UnitSettingsRepository {

  override suspend fun saveUnits(units: UnitsSettings) = Unit

  override fun getUnits(): Flow<UnitsSettings> = flowOf(UnitsSettings(UnitSystem.METRIC))
}
