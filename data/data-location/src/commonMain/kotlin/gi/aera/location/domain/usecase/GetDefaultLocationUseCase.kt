package gi.aera.location.domain.usecase

import gi.aera.location.domain.model.DefaultLocationRepository
import gi.aera.location.domain.model.LocationNotFoundException
import gi.aera.location.domain.model.LocationResult
import gi.aera.location.domain.model.PermissionException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class GetDefaultLocationUseCase(
  private val defaultLocationRepository: DefaultLocationRepository,
  private val getLastGpsLocationUseCase: GetLastGpsLocationUseCase,
  private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) {

  operator fun invoke(): Flow<LocationResult> = defaultLocationRepository.getLocation()
    .map { location -> LocationResult.Success(location) as LocationResult }
    .catch { e ->
      when (e) {
        is LocationNotFoundException -> {
          try {
            emit(LocationResult.Success(getLastGpsLocationUseCase()))
          } catch (_: PermissionException) {
            emit(LocationResult.PermissionRequired)
          } catch (_: Exception) {
            emit(LocationResult.NotFound)
          }
        }

        else -> throw e
      }
    }
    .flowOn(dispatcher)
}
