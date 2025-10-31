package gi.aera.feature.weather.location

import app.cash.turbine.test
import dev.icerock.moko.permissions.DeniedException
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionsController
import dev.icerock.moko.permissions.location.LOCATION
import dev.mokkery.MockMode
import dev.mokkery.answering.returns
import dev.mokkery.answering.throws
import dev.mokkery.every
import dev.mokkery.everySuspend
import dev.mokkery.matcher.any
import dev.mokkery.mock
import gi.aera.common.dispatchers.IoDispatcher
import gi.aera.common.model.ApiResponse
import gi.aera.common.model.AppError
import gi.aera.feature.weather.doubles.FakeNavigatorManager
import gi.aera.feature.weather.doubles.fakeSearchLocation
import gi.aera.location.domain.model.GpsCoordinates
import gi.aera.location.domain.model.LocationNotFoundException
import gi.aera.location.domain.model.LocationSource
import gi.aera.location.domain.repository.DefaultLocationRepository
import gi.aera.location.domain.repository.GpsLocationRepository
import gi.aera.ui.LceState
import gi.aera.ui.navigation.SettingType
import gi.aera.ui.navigation.domain.model.NavigationManager
import gi.aera.weather.feature.location.presentation.WeatherLocationViewModel
import gi.aera.weather.feature.location.presentation.model.WeatherLocationEvent
import gi.aera.weather.forecast.domain.repository.ForecastRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.parameter.parametersOf
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

class WeatherLocationViewModelTest : KoinTest {

  private val testDispatcher: CoroutineDispatcher by inject(named(IoDispatcher))

  private val permissionsController: PermissionsController = mock(MockMode.autoUnit) {
    everySuspend { isPermissionGranted(any()) } returns true
  }
  private val lastGpsLocationRepository: GpsLocationRepository = mock {
    everySuspend { getLastGpsLocation() } returns flowOf(GpsCoordinates(1.0, 1.0))
  }
  private val defaultLocationRepository: DefaultLocationRepository = mock(MockMode.autoUnit) {
    every { getLocation() } returns flowOf(fakeSearchLocation)
  }
  private lateinit var fakeNavigatorManager: FakeNavigatorManager
  private val viewModel: WeatherLocationViewModel by inject { parametersOf(permissionsController) }

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
    declare { lastGpsLocationRepository }
    declare { defaultLocationRepository }

    Dispatchers.setMain(testDispatcher)
  }

  @AfterTest
  fun tearDown() {
    Dispatchers.resetMain()
    stopKoin()
  }

  @Test
  fun `when start collecting weather location, then state is loading`() = runTest {
    viewModel.weatherLocationState.test {
      assertTrue(awaitItem() is LceState.Loading)
    }
  }

  @Test
  fun `when loading weather location, then content state is emitted`() = runTest {
    viewModel.weatherLocationState.test {
      assertTrue(awaitItem() is LceState.Loading)

      viewModel.getWeatherLocation(fakeSearchLocation)

      assertTrue(awaitItem() is LceState.Content)
    }
  }

  @Test
  fun `when request for weather location has network error, then error state is emitted`() = runTest {
    val mockForecastRepository: ForecastRepository = mock(MockMode.autoUnit) {
      everySuspend { realtimeWeather(any()) } returns ApiResponse.Error.NetworkError
    }
    declare<ForecastRepository> { mockForecastRepository }

    viewModel.weatherLocationState.test {
      assertTrue(awaitItem() is LceState.Loading)

      viewModel.getWeatherLocation(fakeSearchLocation)

      val errorState = awaitItem()
      assertTrue(errorState is LceState.Error)
      assertEquals(AppError.NetworkError, errorState.appError)
    }
  }

  @Test
  fun `when navigate to network settings event emitted, then open system settings`() = runTest {

    viewModel.obtainEvent(WeatherLocationEvent.NavigateToNetworkSettings)

    assertEquals(SettingType.NETWORK, fakeNavigatorManager.fakeNavigationSystemSettings.awaitItem())
  }

  @Test
  fun `when loading all weather locations, then content state is emitted`() = runTest {
    viewModel.savedLocationsWeatherState.test {
      assertTrue(awaitItem() is LceState.Loading)

      val stateContent = awaitItem()
      assertTrue(stateContent is LceState.Content)
      assertTrue(stateContent.content.first().searchLocation.source == LocationSource.GPS)
    }
  }

  @Test
  fun `when GPS permission is not granted, then content state has only saved locations`() = runTest {
    val permissionsController: PermissionsController = mock(MockMode.autoUnit) {
      everySuspend { isPermissionGranted(any()) } returns false
      everySuspend { providePermission(any()) } throws DeniedException(Permission.LOCATION)
    }
    val viewModel: WeatherLocationViewModel by inject { parametersOf(permissionsController) }

    viewModel.savedLocationsWeatherState.test {
      assertTrue(awaitItem() is LceState.Loading)

      val stateContent = awaitItem()
      assertTrue(stateContent is LceState.Content)
      assertFalse(stateContent.content.first().searchLocation.source == LocationSource.GPS)
    }
  }

  @Test
  fun `when GPS permission is granted, but location got exception then content state has only saved locations`() = runTest {
    val mockGpsLocationRepository = mock<GpsLocationRepository> {
      everySuspend { getLastGpsLocation() } throws LocationNotFoundException()
    }
    declare<GpsLocationRepository> { mockGpsLocationRepository }

    viewModel.savedLocationsWeatherState.test {
      assertTrue(awaitItem() is LceState.Loading)

      val stateContent = awaitItem()
      assertTrue(stateContent is LceState.Content)
      assertFalse(stateContent.content.first().searchLocation.source == LocationSource.GPS)
    }
  }

  @Test
  fun `when any of the weather requests got timeout exception, then network error state is emitted`() = runTest {
    val mockForecastRepository: ForecastRepository = mock(MockMode.autoUnit) {
      everySuspend { realtimeWeather(any()) } returns ApiResponse.Error.TimeoutError
    }
    declare<ForecastRepository> { mockForecastRepository }

    viewModel.savedLocationsWeatherState.test {
      assertTrue(awaitItem() is LceState.Loading)

      val stateContent = awaitItem()
      assertTrue(stateContent is LceState.Error)
      assertEquals(AppError.NetworkError, stateContent.appError)
    }
  }
}
