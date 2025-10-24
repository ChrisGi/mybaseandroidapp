package gi.aera.network.domain

import gi.aera.common.model.ApiResponse

expect fun mapException(throwable: Throwable): ApiResponse.Error
