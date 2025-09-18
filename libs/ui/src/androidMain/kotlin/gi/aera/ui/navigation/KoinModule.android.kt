package gi.aera.ui.navigation

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual val platformModule = module {
  single { SystemSettingNavigation(androidContext()) }
}
