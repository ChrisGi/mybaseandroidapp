package gi.aera.location.domain.usecase

import gi.aera.location.domain.repository.GpsLocationRepository
import gi.aera.location.domain.model.LocationSource
import gi.aera.location.domain.model.SearchLocation
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class GetLastGpsLocationUseCase(
  private val gpsLocationRepository: GpsLocationRepository,
  private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) {

  suspend operator fun invoke(): SearchLocation = withContext(dispatcher) {
    val gpsCoordinates = gpsLocationRepository.getLastGpsLocation().first()
    return@withContext SearchLocation(
      PLACE_ID,
      null,
      null,
      gpsCoordinates.latitude,
      gpsCoordinates.longitude,
      LocationSource.GPS,
    )
  }

  companion object {
    @OptIn(ExperimentalUuidApi::class)
    private val PLACE_ID = Uuid.random().toString()
  }
}
