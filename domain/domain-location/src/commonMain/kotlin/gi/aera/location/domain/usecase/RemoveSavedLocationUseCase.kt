package gi.aera.location.domain.usecase

import gi.aera.location.domain.model.SearchLocation
import gi.aera.location.domain.repository.DefaultLocationRepository
import gi.aera.location.domain.repository.LocationRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class RemoveSavedLocationUseCase internal constructor(
  private val locationRepository: LocationRepository,
  private val defaultLocationRepository: DefaultLocationRepository,
  private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) {

  suspend operator fun invoke(location: SearchLocation) = withContext(dispatcher) {
    runCatching {
      locationRepository.removeLocation(location.placeId)

      try {
        val defaultLocation = defaultLocationRepository.getLocation().first()
        val isDefaultLocation = defaultLocation.placeId == location.placeId
        if (isDefaultLocation) {
          defaultLocationRepository.removeLocation()
        }
      } catch (_: Exception) {
        println("Not default location, nothing to do")
      }
    }
  }
}
