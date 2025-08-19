package gi.aera.weathertomorrow

import androidx.compose.foundation.background
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
        .background(MaterialTheme.colorScheme.background)
        .safeContentPadding()
        .fillMaxSize(),
    ) {
      val state = LceState.Content(
        listOf(
          WeatherConditions(
            "100",
            UiString.Resource(WeatherCode.CLEAR_SUNNY.conditionStringRes),
            "icon",
            "mon",
            UiString.Text("London"),
            UnitSystem.IMPERIAL,
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
