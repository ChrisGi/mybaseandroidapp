package gi.aera.location.domain.usecase

import gi.aera.location.domain.repository.DefaultLocationRepository
import gi.aera.location.domain.repository.SaveLocationRepository
import gi.aera.location.domain.model.SearchLocation
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class RemoveSavedLocationUseCase internal constructor(
  private val saveLocationRepository: SaveLocationRepository,
  private val defaultLocationRepository: DefaultLocationRepository,
  private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) {

  suspend operator fun invoke(location: SearchLocation) = withContext(dispatcher) {
    runCatching {
      saveLocationRepository.removeLocation(location.placeId)

      val defaultLocation = defaultLocationRepository.getLocation().first()
      if (defaultLocation.placeId == location.placeId) {
        defaultLocationRepository.removeLocation()
      }
    }
  }
}
