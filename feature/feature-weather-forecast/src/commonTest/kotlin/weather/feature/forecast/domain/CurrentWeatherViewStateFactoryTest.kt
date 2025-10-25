package weather.feature.forecast.domain

import gi.aera.common.dispatchers.IoDispatcher
import gi.aera.common.model.successData
import gi.aera.ui.C.DEFAULT_UI_VALUE
import gi.aera.ui.text.UiString
import gi.aera.ui.text.getText
import gi.aera.weather.Res
import gi.aera.weather.domain.model.UnitSystemValues
import gi.aera.weather.domain.model.WeatherCode
import gi.aera.weather.feature.forecast.domain.CurrentWeatherViewStateFactory
import gi.aera.weather.forecast.domain.repository.ForecastRepository
import gi.aera.weather.forecast.data.StubInvalidValuesForecastRepositoryImpl
import gi.aera.weather.forecast.domain.usecase.GetCurrentWeatherUseCase
import gi.aera.weather.weather_condition_wind
import gi.aera.weather.weather_temperature_apparent
import gi.aera.weather.wind
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.qualifier.named
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.mock.declare
import weather.feature.forecast.featureModules
import weather.feature.forecast.mock.CITY
import weather.feature.forecast.mock.fakeSearchLocation
import weather.feature.forecast.testModule
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CurrentWeatherViewStateFactoryTest : KoinTest {

  private val testDispatcher: CoroutineDispatcher by inject(named(IoDispatcher))

  private val currentWeatherViewStateFactory by inject<CurrentWeatherViewStateFactory>()
  private val getCurrentWeatherUseCase by inject<GetCurrentWeatherUseCase>()

  @BeforeTest
  fun setup() {
    startKoin {
      modules(
        featureModules,
        testModule,
      )
    }

    Dispatchers.setMain(testDispatcher)
  }

  @AfterTest
  fun tearDown() {
    stopKoin()
    Dispatchers.resetMain()
  }

  @Test
  fun `should format wind condition in metric unit system`() = runTest {
    val responseData = getCurrentWeatherUseCase(CITY).successData()

    val state = currentWeatherViewStateFactory.createState(responseData, fakeSearchLocation)

    val windCondition = state.conditionValues[0]
    val windValue = windCondition.value.getText()

    assertTrue(state.conditionValues.size == 6)

    val expectValueWithUnit = "4${UnitSystemValues.Metric.windSpeed}"
    assertEquals(expectValueWithUnit, windValue)

    assertEquals(Res.drawable.wind, windCondition.icon)
    assertEquals(UiString.Resource(Res.string.weather_condition_wind), windCondition.description)
  }

  @Test
  fun `should return default when condition is not available`() = runTest {
    declare<ForecastRepository> {
      StubInvalidValuesForecastRepositoryImpl()
    }
    val responseData = getCurrentWeatherUseCase(CITY).successData()

    val state = currentWeatherViewStateFactory.createState(responseData, fakeSearchLocation)

    val windCondition = state.conditionValues[0]
    val windValue = windCondition.value.getText()

    assertTrue(state.conditionValues.size == 6)

    val expectValueWithUnit = DEFAULT_UI_VALUE
    assertEquals(expectValueWithUnit, windValue)
  }

  @Test
  fun `should return formatted temperature apparent`() = runTest {
    val responseData = getCurrentWeatherUseCase(CITY).successData()

    val state = currentWeatherViewStateFactory.createState(responseData, fakeSearchLocation)

    val temperatureApparent = state.weatherConditions.temperatureApparent as UiString.Resource
    val expectValue = UiString.Resource(Res.string.weather_temperature_apparent, 23)

    assertEquals(expectValue, temperatureApparent)
  }

  @Test
  fun `should return empty when temperature apparent is not available`() = runTest {
    declare<ForecastRepository> {
      StubInvalidValuesForecastRepositoryImpl()
    }
    val responseData = getCurrentWeatherUseCase(CITY).successData()

    val state = currentWeatherViewStateFactory.createState(responseData, fakeSearchLocation)

    val temperatureApparent = state.weatherConditions.temperatureApparent
    val expectValue = UiString.Empty

    assertEquals(expectValue, temperatureApparent)
  }

  @Test
  fun `should return proper condition icon`() = runTest {
    val responseData = getCurrentWeatherUseCase(CITY).successData()

    val state = currentWeatherViewStateFactory.createState(responseData, fakeSearchLocation)

    val conditionIcon = state.weatherConditions.conditionIcon
    val expectValue = WeatherCode.fromCode(1101).conditionIcon

    assertEquals(expectValue, conditionIcon)
  }

  @Test
  fun `should return unknown condition icon when weather code is not known`() = runTest {
    declare<ForecastRepository> {
      StubInvalidValuesForecastRepositoryImpl()
    }
    val responseData = getCurrentWeatherUseCase(CITY).successData()

    val state = currentWeatherViewStateFactory.createState(responseData, fakeSearchLocation)

    val conditionIcon = state.weatherConditions.conditionIcon
    val expectValue = WeatherCode.UNKNOWN.conditionIcon

    assertEquals(expectValue, conditionIcon)
  }

  @Test
  fun `should return proper condition title for weather code`() = runTest {
    val responseData = getCurrentWeatherUseCase(CITY).successData()

    val state = currentWeatherViewStateFactory.createState(responseData, fakeSearchLocation)

    val conditionTitle = state.weatherConditions.conditionTitle
    val expectValue = UiString.Resource(WeatherCode.fromCode(1101).conditionStringRes)

    assertEquals(expectValue, conditionTitle)
  }

  @Test
  fun `should return empty condition title when weather code is not known`() = runTest {
    declare<ForecastRepository> {
      StubInvalidValuesForecastRepositoryImpl()
    }
    val responseData = getCurrentWeatherUseCase(CITY).successData()

    val state = currentWeatherViewStateFactory.createState(responseData, fakeSearchLocation)

    val conditionTitle = state.weatherConditions.conditionTitle
    val expectValue = UiString.Empty

    assertEquals(expectValue, conditionTitle)
  }
}
