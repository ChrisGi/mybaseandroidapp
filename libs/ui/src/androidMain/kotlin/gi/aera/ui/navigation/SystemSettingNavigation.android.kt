package gi.aera.ui.navigation

import android.content.Context
import android.content.Intent
import android.provider.Settings
import gi.aera.ui.navigation.domain.model.SystemNavigation

actual class SystemSettingNavigation(private val context: Context) : SystemNavigation {
  actual override fun openSystemSettings(settingType: SettingType) {
    val intent = when (settingType) {
      SettingType.NETWORK -> Intent(Settings.ACTION_WIFI_SETTINGS)
    }
      .apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
      }
    context.startActivity(intent)
  }
}
