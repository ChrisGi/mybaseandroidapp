package gi.aera.feature.weather.search

import app.cash.turbine.ReceiveTurbine
import app.cash.turbine.test
import app.cash.turbine.turbineScope
import dev.mokkery.MockMode
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.matcher.any
import dev.mokkery.mock
import gi.aera.common.dispatchers.IoDispatcher
import gi.aera.common.model.ApiResponse
import gi.aera.common.model.AppError
import gi.aera.feature.weather.doubles.FakeNavigatorManager
import gi.aera.location.domain.repository.SearchLocationRepository
import gi.aera.ui.LceState
import gi.aera.ui.navigation.SettingType
import gi.aera.ui.navigation.domain.model.NavigationManager
import gi.aera.weather.feature.search.presentation.SearchLocationViewModel
import gi.aera.weather.feature.search.presentation.model.SearchLocationEffect
import gi.aera.weather.feature.search.presentation.model.SearchLocationEvent
import gi.aera.weather.feature.search.presentation.model.SearchLocationViewState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.qualifier.named
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.mock.declare
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class SearchLocationViewModelTest : KoinTest {

  private val testDispatcher: CoroutineDispatcher by inject(named(IoDispatcher))

  private val viewModel: SearchLocationViewModel by inject()

  private lateinit var fakeNavigatorManager: FakeNavigatorManager

  @BeforeTest
  fun setup() {
    startKoin {
      modules(
        featureModules,
        testModule,
      )
    }

    fakeNavigatorManager = FakeNavigatorManager()
    declare<NavigationManager> { fakeNavigatorManager }

    Dispatchers.setMain(testDispatcher)
  }

  @AfterTest
  fun tearDown() {
    Dispatchers.resetMain()
    stopKoin()
  }

  private suspend fun ReceiveTurbine<SearchLocationViewState>.searchAndAwaitLoading(query: String) {
    viewModel.obtainEvent(SearchLocationEvent.Search(query))
    awaitItem()
    awaitItem().also { assertTrue(it.isLoading) }
  }

  private suspend fun ReceiveTurbine<SearchLocationViewState>.searchAndAwaitResult(
    query: String,
  ): SearchLocationViewState {
    searchAndAwaitLoading(query)
    return awaitItem()
  }

  @Test
  fun `when start searching, loading state is emitted and then search content is loaded`() = runTest {
    viewModel.searchLocationViewState.test {
      assertFalse(awaitItem().isLoading)

      runCurrent()

      viewModel.obtainEvent(SearchLocationEvent.Search("query"))

      assertFalse(awaitItem().isLoading)
      assertTrue(awaitItem().isLoading)

      val contentState = awaitItem()
      assertTrue(contentState.content.isNotEmpty())
      assertTrue(contentState.locationSearchBarState.expanded)
    }
  }

  @Test
  fun `when close search is called, then expanded state is false`() = runTest {
    viewModel.searchLocationViewState.test {
      awaitItem()
      runCurrent()

      val contentState = searchAndAwaitResult("query")
      assertTrue(contentState.content.isNotEmpty())

      viewModel.obtainEvent(SearchLocationEvent.ClearSearch)

      val notExpandedState = awaitItem()
      assertFalse(notExpandedState.isLoading)
      assertFalse(notExpandedState.locationSearchBarState.expanded)
    }
  }

  @Test
  fun `when result item is clicked, then show location weather info`() = runTest {
    turbineScope {
      val searchLocationState = viewModel.searchLocationViewState.testIn(backgroundScope)
      val searchLocationEffect = viewModel.searchLocationEffect.testIn(backgroundScope)

      searchLocationState.awaitItem()
      runCurrent()

      viewModel.obtainEvent(SearchLocationEvent.Search("query"))

      searchLocationState.awaitItem()
      searchLocationState.awaitItem().also { assertTrue(it.isLoading) }

      val contentState = searchLocationState.awaitItem()
      assertTrue(contentState.content.isNotEmpty())

      viewModel.obtainEvent(SearchLocationEvent.ShowLocationWeather(contentState.content.first()))

      val effect = searchLocationEffect.awaitItem()
      assertTrue(effect is SearchLocationEffect.ShowLocationWeather)
    }
  }

  @Test
  fun `when search result is network error, then show network error state`() = runTest {
    val searchLocationRepository: SearchLocationRepository = mock(MockMode.autoUnit) {
      everySuspend { searchLocation(any()) } returns ApiResponse.Error.NetworkError
    }

    declare<SearchLocationRepository> { searchLocationRepository }

    viewModel.searchLocationViewState.test {
      awaitItem()
      runCurrent()

      val errorState = searchAndAwaitResult("query")

      assertTrue(errorState.content.isEmpty())
      assertEquals(errorState.displayState, LceState.Error(AppError.NetworkError))
    }
  }

  @Test
  fun `when navigate to network settings event emitted, then open system settings`() = runTest {

    viewModel.obtainEvent(SearchLocationEvent.NavigateToNetworkSettings)

    assertEquals(SettingType.NETWORK, fakeNavigatorManager.fakeNavigationSystemSettings.awaitItem())
  }
}
