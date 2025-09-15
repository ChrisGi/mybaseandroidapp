package gi.aera.weather.error

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import gi.aera.ui.navigation.Route
import gi.aera.ui.text.UiString
import gi.aera.weather.error.domain.model.FatalErrorState

fun NavGraphBuilder.genericErrorNavScreen(
  navigateBack: () -> Unit,
) {
  composable<Route.FatalErrorNavScreen> { backStackEntry ->
    val navArgs = backStackEntry.toRoute<Route.FatalErrorNavScreen>()
    val state = FatalErrorState(message = UiString.Text(navArgs.message))
    FatalErrorScreen(
      state = state,
      navigateBack = navigateBack,
    )
  }
}
