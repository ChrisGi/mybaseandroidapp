package gi.aera.appsettings.domain.model

import kotlinx.coroutines.flow.Flow

interface UnitSettingsRepository {
  suspend fun saveUnits(units: UnitsSettings)
  fun getUnits(): Flow<UnitsSettings>
}
