package gi.aera.network.domain

import gi.aera.common.model.ApiResponse
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

actual fun mapException(
  throwable: Throwable,
): ApiResponse.Error = when (throwable) {
  is SocketTimeoutException -> ApiResponse.Error.TimeoutError
  is UnknownHostException,
  is IOException,
    -> ApiResponse.Error.NetworkError

  else -> ApiResponse.Error.UnknownError(throwable.message)
}
