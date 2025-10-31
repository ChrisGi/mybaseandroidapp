package gi.aera.location.domain.usecase

import gi.aera.location.domain.repository.LocationRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.flowOn

class GetSavedLocationUseCase internal constructor(
  private val locationRepository: LocationRepository,
  private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) {

  operator fun invoke() = locationRepository.getLocations()
    .flowOn(dispatcher)
}
