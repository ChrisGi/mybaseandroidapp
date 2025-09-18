package gi.aera.network.domain

import gi.aera.domain.model.ApiResponse

expect fun mapException(throwable: Throwable): ApiResponse.Error
