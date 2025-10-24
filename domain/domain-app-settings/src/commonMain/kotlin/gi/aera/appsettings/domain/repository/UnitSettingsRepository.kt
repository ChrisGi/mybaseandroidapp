package gi.aera.appsettings.domain.repository

import gi.aera.appsettings.domain.model.UnitsSettings
import kotlinx.coroutines.flow.Flow

interface UnitSettingsRepository {
  suspend fun saveUnits(units: UnitsSettings)
  fun getUnits(): Flow<UnitsSettings>
}
