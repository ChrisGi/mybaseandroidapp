package gi.aera.location.data

import gi.aera.location.domain.model.GpsCoordinates
import kotlinx.coroutines.flow.Flow

internal actual class LocationRepositoryImpl : LocationRepository {
  override fun getLastGpsLocation(): Flow<GpsCoordinates> {
    TODO("Not yet implemented")
  }
}
