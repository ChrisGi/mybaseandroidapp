package weather.feature.forecast.presentation

import app.cash.turbine.test
import dev.icerock.moko.permissions.DeniedException
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionsController
import dev.icerock.moko.permissions.RequestCanceledException
import dev.icerock.moko.permissions.location.LOCATION
import dev.mokkery.MockMode
import dev.mokkery.annotations.DelicateMokkeryApi
import dev.mokkery.answering.returns
import dev.mokkery.answering.throws
import dev.mokkery.every
import dev.mokkery.everySuspend
import dev.mokkery.matcher.any
import dev.mokkery.mock
import dev.mokkery.verify.VerifyMode
import dev.mokkery.verifySuspend
import gi.aera.common.dispatchers.IoDispatcher
import gi.aera.location.domain.repository.DefaultLocationRepository
import gi.aera.location.domain.model.GpsCoordinates
import gi.aera.location.domain.repository.GpsLocationRepository
import gi.aera.location.domain.model.LocationNotFoundException
import gi.aera.location.domain.model.LocationResult
import gi.aera.location.domain.model.PermissionException
import gi.aera.location.domain.usecase.GetDefaultLocationUseCase
import gi.aera.ui.LceState
import gi.aera.ui.navigation.Route
import gi.aera.ui.navigation.domain.model.NavigationManager
import gi.aera.ui.navigation.domain.model.SystemNavigation
import gi.aera.weather.feature.forecast.presentation.ForecastViewModel
import gi.aera.weather.feature.forecast.presentation.model.ForecastScreenViewEvent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
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
import weather.feature.forecast.featureModules
import weather.feature.forecast.mock.FakeNavigatorManager
import weather.feature.forecast.mock.fakeSearchLocation
import weather.feature.forecast.testModule
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ForecastViewModelTest : KoinTest {

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
  private val systemSettingNavigation: SystemNavigation = mock {
    every { openSystemSettings(any()) } returns Unit
  }
  private val viewModel: ForecastViewModel by inject { parametersOf(permissionsController) }
  private val navigationManager: NavigationManager = mock(MockMode.autoUnit)

  @OptIn(ExperimentalCoroutinesApi::class)
  @BeforeTest
  fun setup() {
    startKoin {
      modules(
        featureModules,
        testModule,
      )
    }

    declare { systemSettingNavigation }
    declare { navigationManager }

    Dispatchers.setMain(testDispatcher)
  }

  @OptIn(ExperimentalCoroutinesApi::class)
  @AfterTest
  fun tearDown() {
    Dispatchers.resetMain()
    stopKoin()
  }

  @Test
  fun `when start collecting, loading state is emitted and then weather content is loaded`() = runTest {
    declare { lastGpsLocationRepository }
    declare { defaultLocationRepository }

    viewModel.currentWeatherViewState.test {
      assertEquals(LceState.Loading, awaitItem())
      assertTrue(awaitItem() is LceState.Content)
    }
  }

  @Test
  fun `when no default location, and system location permission is denied then map to PermissionRequired`() = runTest {
    val mockDefaultLocationRepository: DefaultLocationRepository = mock(MockMode.autoUnit) {
      every { getLocation() } returns flow { throw LocationNotFoundException() }
    }
    val mockGpsLocationRepository = mock<GpsLocationRepository> {
      everySuspend { getLastGpsLocation() } throws PermissionException()
    }

    declare { mockDefaultLocationRepository }
    declare { mockGpsLocationRepository }

    val getDefaultLocationUseCase: GetDefaultLocationUseCase by inject()

    getDefaultLocationUseCase().test {
      assertTrue(awaitItem() is LocationResult.PermissionRequired)
      awaitComplete()
    }
  }

  @OptIn(DelicateMokkeryApi::class)
  @Test
  fun `when there is no default location, and system location permission is not granted then should ask for permission`() = runTest {
    val mockPermissionsController = mock<PermissionsController>(MockMode.autoUnit) {
      everySuspend { isPermissionGranted(any()) } returns false
    }
    val mockDefaultLocationRepository: DefaultLocationRepository = mock(MockMode.autoUnit) {
      every { getLocation() } returns flow { throw LocationNotFoundException() }
    }

    var locationCallCount = 0
    val mockGpsLocationRepository = mock<GpsLocationRepository> {
      everySuspend { getLastGpsLocation() } returns flow {
        locationCallCount++
        if (locationCallCount == 1) {
          throw PermissionException()
        } else {
          emit(GpsCoordinates(1.0, 1.0))
        }
      }
    }

    declare { mockGpsLocationRepository }
    declare { mockDefaultLocationRepository }

    val testViewModel: ForecastViewModel by inject { parametersOf(mockPermissionsController) }

    testViewModel.currentWeatherViewState.test {
      assertEquals(LceState.Loading, awaitItem())
      assertTrue(awaitItem() is LceState.Content, "Content should be loaded after permission is granted")
    }

    verifySuspend(VerifyMode.exactly(1)) { mockPermissionsController.providePermission(any()) }
    assertEquals(
      2,
      locationCallCount,
      "Location should have been fetched twice - first with PermissionException, then successfully",
    )
  }

  @Test
  fun `when user triggers refresh, refreshing state is emitted before content`() = runTest {
    declare { lastGpsLocationRepository }
    declare { defaultLocationRepository }

    viewModel.currentWeatherViewState.test {
      assertEquals(LceState.Loading, awaitItem())
      val currentWeatherStateContent = awaitItem()
      assertTrue(currentWeatherStateContent is LceState.Content, "Initial content should be loaded")

      viewModel.obtainEvent(ForecastScreenViewEvent.Retry)

      val refreshingState = awaitItem()
      assertTrue(refreshingState is LceState.Refreshing)
      assertEquals(currentWeatherStateContent.content, refreshingState.content, "Content should be loaded alongside refresh")
      assertTrue(awaitItem() is LceState.Content, "Content should be loaded after refresh")
    }
  }

  @Test
  fun `when permission is denied, navigation to search location is triggered`() = runTest {
    val mockPermissionsController = mock<PermissionsController>(MockMode.autoUnit) {
      everySuspend { isPermissionGranted(any()) } returns false
      everySuspend { providePermission(any()) } throws DeniedException(Permission.LOCATION)
    }

    val mockDefaultLocationRepository: DefaultLocationRepository = mock(MockMode.autoUnit) {
      every { getLocation() } returns flow { throw LocationNotFoundException() }
    }

    val mockGpsLocationRepository = mock<GpsLocationRepository> {
      everySuspend { getLastGpsLocation() } throws PermissionException()
    }
    val navigatorManager = FakeNavigatorManager()

    declare<NavigationManager> { navigatorManager }
    declare { mockPermissionsController }
    declare { mockGpsLocationRepository }
    declare { mockDefaultLocationRepository }

    val testViewModel: ForecastViewModel by inject { parametersOf(mockPermissionsController) }

    testViewModel.currentWeatherViewState.test {
      expectMostRecentItem()
      assertEquals(Route.SearchLocationNavScreen, navigatorManager.fakeNavigationRoute.awaitItem().route)
    }
  }

  @Test
  fun `when permission request is canceled and no default location exists, navigate to search location`() = runTest {
    val mockPermissionsController = mock<PermissionsController>(MockMode.autoUnit) {
      everySuspend { isPermissionGranted(any()) } returns false
      everySuspend { providePermission(any()) } throws RequestCanceledException(Permission.LOCATION)
    }

    val mockDefaultLocationRepository: DefaultLocationRepository = mock(MockMode.autoUnit) {
      every { getLocation() } returns flow { throw LocationNotFoundException() }
    }

    val mockGpsLocationRepository = mock<GpsLocationRepository> {
      everySuspend { getLastGpsLocation() } throws PermissionException()
    }

    val navigatorManager = FakeNavigatorManager()

    declare<NavigationManager> { navigatorManager }
    declare { mockPermissionsController }
    declare { mockGpsLocationRepository }
    declare { mockDefaultLocationRepository }

    val testViewModel: ForecastViewModel by inject { parametersOf(mockPermissionsController) }

    testViewModel.currentWeatherViewState.test {
      expectMostRecentItem()
      assertEquals(Route.SearchLocationNavScreen, navigatorManager.fakeNavigationRoute.awaitItem().route)
    }
  }
}
