package gi.aera.weathertomorrow

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import gi.aera.appsettings.domain.model.UnitSystem
import gi.aera.ui.LceState
import gi.aera.ui.text.UiString
import gi.aera.ui.theme.AppTheme
import gi.aera.ui.theme.appTypography
import gi.aera.ui.theme.extraColors
import gi.aera.weather.Res
import gi.aera.weather.domain.model.WeatherCode
import gi.aera.weather.domain.model.toTemperatureUnit
import gi.aera.weather.feature.forecast.domain.ConditionValue
import gi.aera.weather.feature.forecast.domain.CurrentConditions
import gi.aera.weather.feature.forecast.domain.WeatherConditions
import gi.aera.weather.feature.forecast.presentation.ForecastScreen
import gi.aera.weather.humidity
import gi.aera.weather.pressure
import gi.aera.weather.water_drop
import gi.aera.weather.weather_condition_humidity
import gi.aera.weather.weather_condition_precipitation
import gi.aera.weather.weather_condition_pressure

@Preview
@Composable
private fun ForecScreenPreview(
  @PreviewParameter(PreviewUiModeProvider::class) isDarkTheme: Boolean,
) {
  AppTheme(appTypography(), isDarkTheme) {
    Surface(
      modifier = Modifier
        .fillMaxSize(),
    ) {
      val state = LceState.Content(listOf(mockCondition, mockCondition, mockCondition, mockCondition, mockCondition))
      val currentWeatherState = LceState.Content(CurrentConditions(mockCondition, mockOtherConditions))
      Box(
        modifier = Modifier
          .fillMaxSize()
          .background(
            brush = MaterialTheme.extraColors.backgroundGradient,
          ),
      ) {
        ForecastScreen(
          currentWeatherState = currentWeatherState,
          forecastState = state,
          event = {},
        )
      }
    }
  }
}

private val mockCondition = WeatherConditions(
  temperature = "100",
  temperatureApparent = UiString.Text("Odczuwalnie 10"),
  temperatureUnit = UnitSystem.METRIC.toTemperatureUnit(),
  conditionTitle = UiString.Resource(WeatherCode.CLOUDY.conditionStringRes),
  conditionIcon = WeatherCode.CLOUDY.conditionIcon,
  weekday = "mon",
  location = UiString.Text("London"),
)

private val mockOtherConditions = listOf(
  ConditionValue(
    Res.drawable.water_drop,
    UiString.Text("20"),
    UiString.Resource(Res.string.weather_condition_precipitation),
  ),
  ConditionValue(
    Res.drawable.pressure,
    UiString.Text("1024"),
    UiString.Resource(Res.string.weather_condition_pressure),
  ),
  ConditionValue(
    Res.drawable.humidity,
    UiString.Text("67"),
    UiString.Resource(Res.string.weather_condition_humidity),
  ),
  ConditionValue(
    Res.drawable.water_drop,
    UiString.Text("20"),
    UiString.Resource(Res.string.weather_condition_precipitation),
  ),
  ConditionValue(
    Res.drawable.pressure,
    UiString.Text("1024"),
    UiString.Resource(Res.string.weather_condition_pressure),
  ),
  ConditionValue(
    Res.drawable.humidity,
    UiString.Text("67"),
    UiString.Resource(Res.string.weather_condition_humidity),
  ),
)
