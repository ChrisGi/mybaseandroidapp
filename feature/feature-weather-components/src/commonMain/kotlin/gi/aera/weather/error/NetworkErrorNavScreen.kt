package gi.aera.weather.error

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import gi.aera.domain.model.AppError
import gi.aera.ui.navigation.Route

fun NavGraphBuilder.networkErrorNavScreen(
  onBack: () -> Unit,
) {
  composable<Route.NetworkErrorNavScreen> { backStackEntry ->
    val navArgs = backStackEntry.toRoute<Route.NetworkErrorNavScreen>()
    val state = AppError.NetworkError(navArgs.errorCode)
    NetworkErrorScreen(
      appError = state,
      modifier = Modifier,
      onBack = onBack,
    )
  }
}
