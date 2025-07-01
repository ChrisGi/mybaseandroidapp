package gi.aera.location.domain.usecase

import gi.aera.location.data.SaveLocationRepository
import gi.aera.location.domain.model.SearchLocation
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json

class GetSavedLocationUseCase internal constructor(
  private val saveLocationRepository: SaveLocationRepository,
  private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) {

  operator fun invoke() = saveLocationRepository.getSavedLocation()
    .map { json -> Json.decodeFromString(SearchLocation.serializer(), json) }
    .flowOn(dispatcher)
}
