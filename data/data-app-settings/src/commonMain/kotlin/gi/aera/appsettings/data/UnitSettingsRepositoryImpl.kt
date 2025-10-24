package gi.aera.appsettings.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import gi.aera.appsettings.domain.model.UnitSystem
import gi.aera.appsettings.domain.model.UnitsSettings
import gi.aera.appsettings.domain.repository.UnitSettingsRepository
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json

internal class UnitSettingsRepositoryImpl(
  private val dataStore: DataStore<Preferences>,
) : UnitSettingsRepository {

  private val unitSettingKey = stringPreferencesKey(C.DATA_STORE_UNIT_SETTINGS_KEY)

  override suspend fun saveUnits(units: UnitsSettings) {
    val json = Json.encodeToString(UnitsSettings.serializer(), units)
    dataStore.edit { preferences ->
      preferences[unitSettingKey] = json
    }
  }

  override fun getUnits() = dataStore.data
    .map { preferences ->
      val json = preferences[unitSettingKey]
      if (json != null) {
        Json.decodeFromString(UnitsSettings.serializer(), json)
      } else {
        UnitsSettings(UnitSystem.METRIC)
      }
    }
}
