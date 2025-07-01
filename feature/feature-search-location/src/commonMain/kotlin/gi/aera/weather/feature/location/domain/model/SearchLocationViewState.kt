package gi.aera.weather.feature.location.domain.model

import gi.aera.location.domain.model.SearchLocation
import gi.aera.ui.LceState
import gi.aera.ui.text.UiString
import gi.aera.weather.Res
import gi.aera.weather.search_location_screen_title

data class SearchLocationViewState(
  val toolbarTitle: UiString = UiString.Resource(Res.string.search_location_screen_title),
  val searchQuery: String = "",
  val displayState: LceState<List<SearchLocation>>? = null,
) {

  val isLoading: Boolean
    get() = displayState == LceState.Loading

  val content: List<SearchLocation>
    get() = (displayState as? LceState.Content)?.content ?: emptyList()

  val isEmpty: Boolean
    get() = (displayState as? LceState.Content) != null && content.isEmpty()
}
