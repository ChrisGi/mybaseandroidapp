package gi.aera.location.data

import gi.aera.location.domain.model.GpsCoordinates
import gi.aera.location.domain.repository.GpsLocationRepository
import kotlinx.coroutines.flow.Flow

internal actual class GpsLocationRepositoryImpl : GpsLocationRepository {
  actual override fun getLastGpsLocation(): Flow<GpsCoordinates> {
    TODO("Not yet implemented")
  }
}
