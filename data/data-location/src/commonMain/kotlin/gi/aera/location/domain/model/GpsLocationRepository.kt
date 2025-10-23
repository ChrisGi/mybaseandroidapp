package gi.aera.location.domain.model

import kotlinx.coroutines.flow.Flow

interface GpsLocationRepository {

  fun getLastGpsLocation(): Flow<GpsCoordinates>
}
