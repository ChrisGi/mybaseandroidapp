package gi.aera.weathertomorrow

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import gi.aera.appsettings.domain.model.UnitSystem
import gi.aera.ui.LceState
import gi.aera.ui.text.UiString
import gi.aera.ui.theme.AppTheme
import gi.aera.ui.theme.appTypography
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
      val state = LceState.Content(
        listOf(
          WeatherConditions(
            temperature = "100",
            conditionTitle = UiString.Resource(WeatherCode.CLEAR_SUNNY.conditionStringRes),
            conditionIcon = WeatherCode.CLEAR_SUNNY.conditionIcon,
            weekday = "mon",
            location = UiString.Text("London"),
            unitSystem = UnitSystem.IMPERIAL,
          ),
        ),
      )
      val currentWeatherState = LceState.Loading
      ForecastScreen(
        currentWeatherState = currentWeatherState,
        forecastState = state,
        {},
      )
    }
  }
}
