package gi.aera.location.data

import gi.aera.location.domain.model.GpsCoordinates
import gi.aera.location.domain.repository.GpsLocationRepository
import kotlinx.coroutines.flow.Flow

internal expect class GpsLocationRepositoryImpl : GpsLocationRepository {
  override fun getLastGpsLocation(): Flow<GpsCoordinates>
}
