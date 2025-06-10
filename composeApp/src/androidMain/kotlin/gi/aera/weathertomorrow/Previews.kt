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
import gi.aera.ui.LceState
import gi.aera.weather.feature.forecast.domain.ForecastViewState
import gi.aera.weather.feature.forecast.domain.WeatherCode
import gi.aera.weather.feature.forecast.presentation.ForecastScreen
import gi.aera.weather.feature.forecast.theme.AppTheme
import gi.aera.weather.feature.forecast.theme.appTypography
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Preview
@Composable
private fun ForecScreenPreview() {
  AppTheme(false, appTypography()) {
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier
        .background(MaterialTheme.colorScheme.background)
        .safeContentPadding()
        .fillMaxSize(),
    ) {
      val localDate = Clock.System.now().toLocalDateTime(TimeZone.UTC)
      val state = LceState.Success(
        listOf(
          ForecastViewState(
            "100",
            WeatherCode.CLEAR_SUNNY.conditionStringRes,
            "icon",
            localDate.date,
          ),
        ),
      )
      ForecastScreen(state)
    }
  }
}
