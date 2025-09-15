package gi.aera.weather.error

import gi.aera.domain.model.AppError
import gi.aera.ui.navigation.NavigationArgs
import gi.aera.ui.navigation.NavigationManager
import gi.aera.ui.navigation.Route
import gi.aera.ui.text.UiString
import gi.aera.weather.Res
import gi.aera.weather.error_fatal

class ErrorScreenNavigationManager(
  private val navigationManager: NavigationManager,
) {

  suspend fun navigateToErrorScreen(appError: AppError) {
    when (appError) {
      AppError.FatalError -> navigationManager.navigateToAsync(
        NavigationArgs(
          route = Route.FatalErrorNavScreen(UiString.Resource(Res.string.error_fatal).asStringAsync()),
          popUpTo = Pair(Route.ForecastNavScreen, true),
        ),
      )

      is AppError.NetworkError -> {
        navigationManager.navigateToAsync(
          NavigationArgs(
            route = Route.NetworkErrorNavScreen(
              appError.errorCode,
            ),
            popUpTo = Pair(Route.ForecastNavScreen, true),
          ),
        )
      }

      is AppError.BusinessError -> navigationManager.navigateToAsync(
        NavigationArgs(
          route = Route.FatalErrorNavScreen(UiString.Text(appError.message).asStringAsync()),
        ),
      )
    }
  }
}
