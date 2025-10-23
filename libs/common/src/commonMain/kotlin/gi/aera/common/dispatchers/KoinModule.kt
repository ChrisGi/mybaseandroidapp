@file:Suppress("TopLevelPropertyNaming")
package gi.aera.common.dispatchers

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dispatchersKoinModule = module {
  single(named(IoDispatcher)) { Dispatchers.IO }
  single(named(MainDispatcher)) { Dispatchers.Main }
  single(named(DefaultDispatcher)) { Dispatchers.Default }
  single(named(UnconfinedDispatcher)) { Dispatchers.Unconfined }
}

const val IoDispatcher = "Dispatchers.IO"
const val MainDispatcher = "Dispatchers.Main"
const val DefaultDispatcher = "Dispatchers.Default"
const val UnconfinedDispatcher = "Dispatchers.Unconfined"
