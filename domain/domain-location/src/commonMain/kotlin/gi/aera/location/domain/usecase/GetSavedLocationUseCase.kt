package gi.aera.location.domain.usecase

import gi.aera.location.domain.repository.SaveLocationRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.flowOn

class GetSavedLocationUseCase internal constructor(
  private val saveLocationRepository: SaveLocationRepository,
  private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) {

  operator fun invoke() = saveLocationRepository.getLocations()
    .flowOn(dispatcher)
}
