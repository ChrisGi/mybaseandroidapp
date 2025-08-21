package gi.aera.location.domain.usecase

import gi.aera.location.data.LocationRepository
import gi.aera.location.domain.model.LocationSource
import gi.aera.location.domain.model.SearchLocation
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class GetLastLocationUseCase internal constructor(
  private val locationRepository: LocationRepository,
  private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) {

  suspend operator fun invoke(): SearchLocation = withContext(dispatcher) {
    val (latitude, longitude) = locationRepository.getLastLocation()
    return@withContext SearchLocation(PLACE_ID, null, null, latitude, longitude, LocationSource.GPS)
  }

  companion object {
    @OptIn(ExperimentalUuidApi::class)
    private val PLACE_ID = Uuid.random().toString()
  }
}
