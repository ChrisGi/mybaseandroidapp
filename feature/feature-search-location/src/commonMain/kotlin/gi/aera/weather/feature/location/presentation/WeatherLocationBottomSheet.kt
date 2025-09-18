package gi.aera.weather.feature.location.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import gi.aera.ui.FullscreenProgressIndicator
import gi.aera.ui.LceState
import gi.aera.ui.LceViewState
import gi.aera.weather.Res
import gi.aera.weather.error.AppErrorContentProvider
import gi.aera.weather.feature.location.domain.WeatherLocationEvent
import gi.aera.weather.feature.location.domain.WeatherLocationState
import gi.aera.weather.weather_location_add
import gi.aera.weather.weather_location_cancel
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherLocationBottomSheet(
  state: LceState<WeatherLocationState.WeatherLocation>,
  event: (WeatherLocationEvent) -> Unit,
  modifier: Modifier = Modifier
    .wrapContentSize()
    .padding(16.dp),
  sheetState: SheetState = rememberModalBottomSheetState(),
  hideBottomSheet: () -> Unit = {},
) {
  ModalBottomSheet(
    sheetState = sheetState,
    onDismissRequest = { hideBottomSheet() },
    containerColor = MaterialTheme.colorScheme.surface,
    tonalElevation = 10.dp,
  ) {
    LceViewState(
      state = state,
      errorContent = {
        AppErrorContentProvider(
          it,
          Modifier
            .padding(32.dp),
          onRetry = { hideBottomSheet() },
          onCheckNetwork = { event(WeatherLocationEvent.NavigateToNetworkSettings) },
        )
      },
      loading = { FullscreenProgressIndicator(modifier.align(Alignment.CenterHorizontally)) },
    ) { weatherLocation ->
      Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp),
      ) {
        TextButton(
          onClick = { hideBottomSheet() },
        ) {
          Text(text = stringResource(Res.string.weather_location_cancel))
        }

        TextButton(
          onClick = {
            hideBottomSheet()
            event(WeatherLocationEvent.Save(weatherLocation.searchLocation))
          },
        ) {
          Text(text = stringResource(Res.string.weather_location_add))
        }
      }

      WeatherLocationCard(
        state = weatherLocation,
        modifier = modifier,
      )
    }
  }
}
