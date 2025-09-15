package gi.aera.weathertomorrow

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import gi.aera.ui.text.UiString
import gi.aera.ui.theme.AppTheme
import gi.aera.ui.theme.appTypography
import gi.aera.weather.error.FatalErrorScreen
import gi.aera.weather.error.domain.model.FatalErrorState

@Preview
@Composable
private fun FatalErrorScreenPreview() {
  AppTheme(appTypography(), false) {
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier
        .background(MaterialTheme.colorScheme.background)
        .fillMaxSize(),
    ) {
      val state = FatalErrorState(
        toolbarTitle = UiString.Empty,
        message = UiString.Text(
          "Lorem ipsum dolor sit amet, " +
            "consectetur adipiscing elit. Suspendisse at aliquam orci, " +
            "et vehicula urna. Nunc pharetra faucibus vestibulum. " +
            "Aenean vitae diam in tellus egestas tempus. Nunc libero massa, " +
            "luctus eget scelerisque et, dignissim et tortor. " +
            "Mauris congue ligula a lectus placerat facilisis. " +
            "Donec et efficitur lectus.",
        ),
      )
      FatalErrorScreen(
        state = state,
        navigateBack = {},
      )
    }
  }
}
