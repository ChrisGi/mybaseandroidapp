package gi.aera.location.domain.usecase

import gi.aera.location.domain.repository.LocationRepository
import gi.aera.location.domain.model.SearchLocation
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class SaveLocationUseCase internal constructor(
  private val locationRepository: LocationRepository,
  private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) {

  suspend operator fun invoke(location: SearchLocation): Result<Unit> = withContext(dispatcher) {
    val locations = locationRepository.getLocations()
      .first()
      .plus(location)
      .distinct()

    runCatching { locationRepository.saveLocations(locations) }
  }
}
