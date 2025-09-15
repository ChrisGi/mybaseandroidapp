package gi.aera.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController

@Composable
fun NavigationLaunchedEffect(
  navigationManager: NavigationManager,
  navController: NavHostController,
) {
  LaunchedEffect(Unit) {
    navigationManager.navigationRoute.collect { navigationArgs ->
      when (navigationArgs.route) {
        is Route.Up -> navController.navigateUp()
        else -> navController.navigate(navigationArgs.route) {
          navigationArgs.popUpTo?.let { (route, inclusive) ->
            popUpTo(route) { this.inclusive = inclusive }
          }
        }
      }
    }
  }
}
