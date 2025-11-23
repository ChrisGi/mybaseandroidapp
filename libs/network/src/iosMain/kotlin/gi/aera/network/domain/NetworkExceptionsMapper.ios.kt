package gi.aera.network.domain

import gi.aera.common.model.ApiResponse

actual fun mapException(throwable: Throwable): ApiResponse.Error {
  return ApiResponse.Error.UnknownError(throwable.message)
}
