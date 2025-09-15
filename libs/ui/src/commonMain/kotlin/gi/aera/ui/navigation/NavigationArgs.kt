package gi.aera.ui.navigation

data class NavigationArgs(
  val route: Route,
  val popUpTo: Pair<Any, Boolean>? = null,
)
