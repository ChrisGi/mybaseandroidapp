package gi.aera.weathertomorrow

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import gi.aera.appsettings.domain.model.UnitSystem
import gi.aera.ui.LceState
import gi.aera.ui.text.UiString
import gi.aera.ui.theme.AppTheme
import gi.aera.ui.theme.appTypography
import gi.aera.ui.theme.extraColors
import gi.aera.weather.domain.model.WeatherCode
import gi.aera.weather.feature.forecast.domain.WeatherConditions
import gi.aera.weather.feature.forecast.presentation.ForecastScreen

@Preview
@Composable
private fun ForecScreenPreview() {
  AppTheme(appTypography(), false) {
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier
        .safeContentPadding()
        .fillMaxSize(),
    ) {
      val mockCondition = WeatherConditions(
        temperature = "100",
        temperatureApparent = UiString.Text("Odczuwalnie 10"),
        conditionTitle = UiString.Resource(WeatherCode.CLEAR_SUNNY.conditionStringRes),
        conditionIcon = WeatherCode.CLEAR_SUNNY.conditionIcon,
        weekday = "mon",
        location = UiString.Text("London"),
        unitSystem = UnitSystem.IMPERIAL,
      )
      val state = LceState.Content(listOf(mockCondition))
      val currentWeatherState = LceState.Content(mockCondition)
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
