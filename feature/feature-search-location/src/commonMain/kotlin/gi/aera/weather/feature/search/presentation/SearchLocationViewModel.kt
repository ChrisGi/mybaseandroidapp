package gi.aera.weather.feature.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gi.aera.domain.model.ApiResponse
import gi.aera.location.domain.model.SearchLocation
import gi.aera.location.domain.usecase.SearchLocationUseCase
import gi.aera.ui.C
import gi.aera.ui.EventHandler
import gi.aera.ui.LceState
import gi.aera.weather.feature.search.domain.model.SearchLocationEffect
import gi.aera.weather.feature.search.domain.model.SearchLocationEvent
import gi.aera.weather.feature.search.domain.model.SearchLocationViewState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@Suppress("OPT_IN_USAGE")
class SearchLocationViewModel(
  private val searchLocationUseCase: SearchLocationUseCase,
) : ViewModel(), EventHandler<SearchLocationEvent> {

  private val _searchLocationViewState = MutableStateFlow(SearchLocationViewState())
  val searchLocationViewState: StateFlow<SearchLocationViewState> = _searchLocationViewState
    .stateIn(
      viewModelScope,
      SharingStarted.WhileSubscribed(C.RELOADING_TIMEOUT),
      SearchLocationViewState(),
    )

  private val _searchLocationEffect = MutableSharedFlow<SearchLocationEffect>()
  val searchLocationEffect = _searchLocationEffect.asSharedFlow()

  init {
    _searchLocationViewState
      .map { it.locationSearchBarState.queryValue }
      .debounce(DEBOUNCE_SEARCH_FOR)
      .filter(::searchWithMinimumQueryLength)
      .distinctUntilChanged()
      .onEach { performNetworkSearchLocation(it) }
      .launchIn(viewModelScope)
  }

  override fun obtainEvent(event: SearchLocationEvent) {
    when (event) {
      is SearchLocationEvent.Search -> searchLocation(event.query)
      is SearchLocationEvent.ShowLocationWeather -> showLocationWeather(event.location)
    }
  }

  fun closeSearch() {
    searchLocation("")
  }

  private fun showLocationWeather(location: SearchLocation) = viewModelScope.launch {
    _searchLocationEffect.emit(SearchLocationEffect.ShowLocationWeather(location))
  }

  private fun searchLocation(query: String) {
    val expanded = query.isNotEmpty()
    _searchLocationViewState.update {
      it.copy(
        locationSearchBarState = it.locationSearchBarState.copy(queryValue = query, expanded = expanded),
      )
    }
  }

  private fun searchWithMinimumQueryLength(query: String, minLength: Int = 3) =
    query.isNotEmpty() && query.length >= minLength

  private suspend fun performNetworkSearchLocation(query: String) {
    _searchLocationViewState.update { it.copy(displayState = LceState.Loading) }

    when (val searchedLocationResponse = searchLocationUseCase(query)) {
      is ApiResponse.Error -> println(searchedLocationResponse.errorMessage)
      is ApiResponse.Success<List<SearchLocation>> -> {
        val searchedLocations = searchedLocationResponse.data
        _searchLocationViewState.update { it.copy(displayState = LceState.Content(searchedLocations)) }
      }
    }
  }

  companion object {
    private const val DEBOUNCE_SEARCH_FOR = 500L
  }
}
