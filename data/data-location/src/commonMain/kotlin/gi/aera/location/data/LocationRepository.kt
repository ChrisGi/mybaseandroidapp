package gi.aera.location.data

import gi.aera.location.domain.model.GpsCoordinates
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
  fun getLastGpsLocation(): Flow<GpsCoordinates>
}
