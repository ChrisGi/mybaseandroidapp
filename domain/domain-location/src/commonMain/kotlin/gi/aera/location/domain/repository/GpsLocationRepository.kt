package gi.aera.location.domain.repository

import gi.aera.location.domain.model.GpsCoordinates
import kotlinx.coroutines.flow.Flow

interface GpsLocationRepository {

  fun getLastGpsLocation(): Flow<GpsCoordinates>
}
