package gi.aera.location.data

import gi.aera.location.domain.model.GpsCoordinates
import gi.aera.location.domain.model.LocationNotFoundException
import gi.aera.location.domain.repository.GpsLocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal actual class GpsLocationRepositoryImpl : GpsLocationRepository {
  actual override fun getLastGpsLocation(): Flow<GpsCoordinates> = flow {
    throw LocationNotFoundException()
  }
}
