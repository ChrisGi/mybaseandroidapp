package gi.aera.weather.feature.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gi.aera.common.model.ApiResponse
import gi.aera.common.model.AppError
import gi.aera.location.domain.model.SearchLocation
import gi.aera.location.domain.usecase.SearchLocationUseCase
import gi.aera.ui.C
import gi.aera.ui.EventHandler
import gi.aera.ui.LceState
import gi.aera.ui.navigation.SettingType
import gi.aera.ui.navigation.domain.model.NavigationManager
import gi.aera.weather.feature.search.presentation.model.SearchLocationEffect
import gi.aera.weather.feature.search.presentation.model.SearchLocationEvent
import gi.aera.weather.feature.search.presentation.model.SearchLocationViewState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchLocationViewModel(
  private val searchLocationUseCase: SearchLocationUseCase,
  private val navigationManager: NavigationManager,
) : ViewModel(), EventHandler<SearchLocationEvent> {

  private val _searchLocationViewState = MutableStateFlow(SearchLocationViewState())
  val searchLocationViewState: StateFlow<SearchLocationViewState> = _searchLocationViewState
    .stateIn(
      viewModelScope,
      SharingStarted.WhileSubscribed(C.RELOADING_TIMEOUT),
      SearchLocationViewState(),
    )

  private val _searchLocationEffect = MutableSharedFlow<SearchLocationEffect>(extraBufferCapacity = 1)
  val searchLocationEffect = _searchLocationEffect.asSharedFlow()

  private val search = MutableSharedFlow<String>(extraBufferCapacity = 1)

  init {
    viewModelScope.launch {
      search
        .debounce(DEBOUNCE_SEARCH_FOR)
        .filter(::hasMinimumQueryLength)
        .collectLatest {
          performNetworkSearchLocation(it)
        }
    }
  }

  override fun obtainEvent(event: SearchLocationEvent) {
    when (event) {
      is SearchLocationEvent.Search -> searchLocation(event.query)
      is SearchLocationEvent.ShowLocationWeather -> showLocationWeather(event.location)
      SearchLocationEvent.ClearSearch -> clearSearch()
      SearchLocationEvent.NavigateToNetworkSettings -> openSystemNetworkSettings()
      SearchLocationEvent.RetrySearch -> search.tryEmit(_searchLocationViewState.value.locationSearchBarState.queryValue)
    }
  }

  private fun openSystemNetworkSettings() {
    navigationManager.openSystemSettings(SettingType.NETWORK)
  }

  private fun showLocationWeather(location: SearchLocation) {
    _searchLocationEffect.tryEmit(SearchLocationEffect.ShowLocationWeather(location))
  }

  private fun clearSearch() {
    _searchLocationViewState.update {
      it.copy(
        locationSearchBarState = it.locationSearchBarState.copy(queryValue = "", expanded = false),
        displayState = null,
      )
    }
  }

  private fun searchLocation(query: String) {
    val expanded = query.isNotEmpty()
    _searchLocationViewState.update {
      it.copy(
        locationSearchBarState = it.locationSearchBarState.copy(queryValue = query, expanded = expanded),
      )
    }
    search.tryEmit(query)
  }

  private fun hasMinimumQueryLength(query: String, minLength: Int = MIN_SEARCH_QUERY_LENGTH) =
    query.length >= minLength

  private suspend fun performNetworkSearchLocation(query: String) {
    _searchLocationViewState.update { it.copy(displayState = LceState.Loading) }

    when (val searchedLocationResponse = searchLocationUseCase(query)) {
      is ApiResponse.Error -> {
        _searchLocationViewState.update { it.copy(displayState = LceState.Error(AppError.from(searchedLocationResponse))) }
      }

      is ApiResponse.Success<List<SearchLocation>> -> {
        val searchedLocations = searchedLocationResponse.data
        _searchLocationViewState.update { it.copy(displayState = LceState.Content(searchedLocations)) }
      }
    }
  }

  companion object {
    private const val DEBOUNCE_SEARCH_FOR = 500L
    private const val MIN_SEARCH_QUERY_LENGTH = 3
  }
}
