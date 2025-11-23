package gi.aera.ui.navigation

import gi.aera.ui.navigation.domain.model.SystemNavigation
import org.koin.dsl.bind
import org.koin.dsl.module

actual val platformModule = module {
  single { SystemSettingNavigation() } bind SystemNavigation::class
}
