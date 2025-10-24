package gi.aera.network.domain

import gi.aera.common.model.ApiResponse
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.statement.HttpResponse
import kotlinx.serialization.SerializationException
import kotlin.coroutines.cancellation.CancellationException

@Suppress("TooGenericExceptionCaught")
suspend inline fun <reified T> apiRequest(
  httpResponse: () -> HttpResponse,
): ApiResponse<T> =
  try {
    ApiResponse.Success(httpResponse().body())
  } catch (exception: ClientRequestException) {
    ApiResponse.Error.HttpError(
      code = exception.response.status.value,
      errorBody = exception.response.body(),
      errorMessage = exception.message,
    )
  } catch (_: HttpRequestTimeoutException) {
    ApiResponse.Error.TimeoutError
  } catch (e: SerializationException) {
    ApiResponse.Error.SerializationError(e.message)
  } catch (c: CancellationException) {
    throw c
  } catch (e: Exception) {
    mapException(e)
  }

inline fun <T, S> ApiResponse<T>.map(crossinline mapper: (T) -> S): ApiResponse<S> {
  return when (this) {
    is ApiResponse.Error -> this
    is ApiResponse.Success<*> -> mapper(data as T).let { ApiResponse.Success(it) }
  }
}
