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
      when (val args = navigationArgs.route) {
        is Route.Up -> navController.navigateUp()
        is Route.SystemSettings -> navigationManager.openSystemSettings(args.settingType)
        else -> navController.navigate(navigationArgs.route) {
          navigationArgs.popUpTo?.let { (route, inclusive) ->
            popUpTo(route) { this.inclusive = inclusive }
          }
        }
      }
    }
  }
}
