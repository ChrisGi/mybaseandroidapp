package gi.aera.weather.presentation.model

import gi.aera.ui.text.UiString
import gi.aera.weather.Res
import gi.aera.weather.clear_day
import gi.aera.weather.cloudy
import gi.aera.weather.drizzle
import gi.aera.weather.flurries
import gi.aera.weather.fog
import gi.aera.weather.fog_light
import gi.aera.weather.freezing_drizzle
import gi.aera.weather.freezing_rain
import gi.aera.weather.freezing_rain_heavy
import gi.aera.weather.freezing_rain_light
import gi.aera.weather.ice_pellets
import gi.aera.weather.ice_pellets_heavy
import gi.aera.weather.ice_pellets_light
import gi.aera.weather.mostly_clear_day
import gi.aera.weather.mostly_cloudy
import gi.aera.weather.partly_cloudy_day
import gi.aera.weather.rain
import gi.aera.weather.rain_heavy
import gi.aera.weather.rain_light
import gi.aera.weather.snow
import gi.aera.weather.snow_heavy
import gi.aera.weather.snow_light
import gi.aera.weather.tstorm
import gi.aera.weather.weather_code_0
import gi.aera.weather.weather_code_1000
import gi.aera.weather.weather_code_1001
import gi.aera.weather.weather_code_1100
import gi.aera.weather.weather_code_1101
import gi.aera.weather.weather_code_1102
import gi.aera.weather.weather_code_1103
import gi.aera.weather.weather_code_2000
import gi.aera.weather.weather_code_2100
import gi.aera.weather.weather_code_2101
import gi.aera.weather.weather_code_2102
import gi.aera.weather.weather_code_2103
import gi.aera.weather.weather_code_2106
import gi.aera.weather.weather_code_2107
import gi.aera.weather.weather_code_2108
import gi.aera.weather.weather_code_4000
import gi.aera.weather.weather_code_4001
import gi.aera.weather.weather_code_4200
import gi.aera.weather.weather_code_4201
import gi.aera.weather.weather_code_4202
import gi.aera.weather.weather_code_4203
import gi.aera.weather.weather_code_4204
import gi.aera.weather.weather_code_4205
import gi.aera.weather.weather_code_4208
import gi.aera.weather.weather_code_4209
import gi.aera.weather.weather_code_4210
import gi.aera.weather.weather_code_4211
import gi.aera.weather.weather_code_4212
import gi.aera.weather.weather_code_4213
import gi.aera.weather.weather_code_4214
import gi.aera.weather.weather_code_4215
import gi.aera.weather.weather_code_5000
import gi.aera.weather.weather_code_5001
import gi.aera.weather.weather_code_5100
import gi.aera.weather.weather_code_5101
import gi.aera.weather.weather_code_5102
import gi.aera.weather.weather_code_5103
import gi.aera.weather.weather_code_5104
import gi.aera.weather.weather_code_5105
import gi.aera.weather.weather_code_5106
import gi.aera.weather.weather_code_5107
import gi.aera.weather.weather_code_5108
import gi.aera.weather.weather_code_5110
import gi.aera.weather.weather_code_5112
import gi.aera.weather.weather_code_5114
import gi.aera.weather.weather_code_5115
import gi.aera.weather.weather_code_5116
import gi.aera.weather.weather_code_5117
import gi.aera.weather.weather_code_5119
import gi.aera.weather.weather_code_5120
import gi.aera.weather.weather_code_5121
import gi.aera.weather.weather_code_5122
import gi.aera.weather.weather_code_6000
import gi.aera.weather.weather_code_6001
import gi.aera.weather.weather_code_6002
import gi.aera.weather.weather_code_6003
import gi.aera.weather.weather_code_6004
import gi.aera.weather.weather_code_6200
import gi.aera.weather.weather_code_6201
import gi.aera.weather.weather_code_6202
import gi.aera.weather.weather_code_6203
import gi.aera.weather.weather_code_6204
import gi.aera.weather.weather_code_6205
import gi.aera.weather.weather_code_6206
import gi.aera.weather.weather_code_6207
import gi.aera.weather.weather_code_6208
import gi.aera.weather.weather_code_6209
import gi.aera.weather.weather_code_6212
import gi.aera.weather.weather_code_6213
import gi.aera.weather.weather_code_6214
import gi.aera.weather.weather_code_6215
import gi.aera.weather.weather_code_6220
import gi.aera.weather.weather_code_6222
import gi.aera.weather.weather_code_7000
import gi.aera.weather.weather_code_7101
import gi.aera.weather.weather_code_7102
import gi.aera.weather.weather_code_7103
import gi.aera.weather.weather_code_7105
import gi.aera.weather.weather_code_7106
import gi.aera.weather.weather_code_7107
import gi.aera.weather.weather_code_7108
import gi.aera.weather.weather_code_7109
import gi.aera.weather.weather_code_7110
import gi.aera.weather.weather_code_7111
import gi.aera.weather.weather_code_7112
import gi.aera.weather.weather_code_7113
import gi.aera.weather.weather_code_7114
import gi.aera.weather.weather_code_7115
import gi.aera.weather.weather_code_7116
import gi.aera.weather.weather_code_7117
import gi.aera.weather.weather_code_8000
import gi.aera.weather.weather_code_8001
import gi.aera.weather.weather_code_8002
import gi.aera.weather.weather_code_8003
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

@Suppress("MagicNumber")
enum class WeatherCode(
  val code: Int,
  val conditionStringRes: StringResource,
  val conditionIcon: DrawableResource,
) {
  UNKNOWN(0, Res.string.weather_code_0, Res.drawable.clear_day),
  CLEAR_SUNNY(1000, Res.string.weather_code_1000, Res.drawable.clear_day),
  MOSTLY_CLEAR(1100, Res.string.weather_code_1100, Res.drawable.mostly_clear_day),
  PARTLY_CLOUDY(1101, Res.string.weather_code_1101, Res.drawable.partly_cloudy_day),
  MOSTLY_CLOUDY(1102, Res.string.weather_code_1102, Res.drawable.mostly_cloudy),
  CLOUDY(1001, Res.string.weather_code_1001, Res.drawable.cloudy),
  PARTLY_CLOUDY_AND_MOSTLY_CLEAR(1103, Res.string.weather_code_1103, Res.drawable.partly_cloudy_day),
  LIGHT_FOG(2100, Res.string.weather_code_2100, Res.drawable.fog_light),
  MOSTLY_CLEAR_AND_LIGHT_FOG(2101, Res.string.weather_code_2101, Res.drawable.fog_light),
  PARTLY_CLOUDY_AND_LIGHT_FOG(2102, Res.string.weather_code_2102, Res.drawable.fog_light),
  MOSTLY_CLOUDY_AND_LIGHT_FOG(2103, Res.string.weather_code_2103, Res.drawable.fog_light),
  MOSTLY_CLEAR_AND_FOG(2106, Res.string.weather_code_2106, Res.drawable.fog),
  PARTLY_CLOUDY_AND_FOG(2107, Res.string.weather_code_2107, Res.drawable.fog),
  MOSTLY_CLOUDY_AND_FOG(2108, Res.string.weather_code_2108, Res.drawable.fog),
  FOG(2000, Res.string.weather_code_2000, Res.drawable.fog),
  PARTLY_CLOUDY_AND_DRIZZLE(4204, Res.string.weather_code_4204, Res.drawable.drizzle),
  MOSTLY_CLEAR_AND_DRIZZLE(4203, Res.string.weather_code_4203, Res.drawable.drizzle),
  MOSTLY_CLOUDY_AND_DRIZZLE(4205, Res.string.weather_code_4205, Res.drawable.drizzle),
  DRIZZLE(4000, Res.string.weather_code_4000, Res.drawable.drizzle),
  LIGHT_RAIN(4200, Res.string.weather_code_4200, Res.drawable.rain_light),
  MOSTLY_CLEAR_AND_LIGHT_RAIN(4213, Res.string.weather_code_4213, Res.drawable.rain_light),
  PARTLY_CLOUDY_AND_LIGHT_RAIN(4214, Res.string.weather_code_4214, Res.drawable.rain_light),
  MOSTLY_CLOUDY_AND_LIGHT_RAIN(4215, Res.string.weather_code_4215, Res.drawable.rain_light),
  MOSTLY_CLEAR_AND_RAIN(4209, Res.string.weather_code_4209, Res.drawable.rain),
  PARTLY_CLOUDY_AND_RAIN(4208, Res.string.weather_code_4208, Res.drawable.rain),
  MOSTLY_CLOUDY_AND_RAIN(4210, Res.string.weather_code_4210, Res.drawable.rain),
  RAIN(4001, Res.string.weather_code_4001, Res.drawable.rain),
  MOSTLY_CLEAR_AND_HEAVY_RAIN(4211, Res.string.weather_code_4211, Res.drawable.rain_heavy),
  PARTLY_CLOUDY_AND_HEAVY_RAIN(4202, Res.string.weather_code_4202, Res.drawable.rain_heavy),
  MOSTLY_CLOUDY_AND_HEAVY_RAIN(4212, Res.string.weather_code_4212, Res.drawable.rain_heavy),
  HEAVY_RAIN(4201, Res.string.weather_code_4201, Res.drawable.rain_heavy),
  MOSTLY_CLEAR_AND_FLURRIES(5115, Res.string.weather_code_5115, Res.drawable.flurries),
  PARTLY_CLOUDY_AND_FLURRIES(5116, Res.string.weather_code_5116, Res.drawable.flurries),
  MOSTLY_CLOUDY_AND_FLURRIES(5117, Res.string.weather_code_5117, Res.drawable.flurries),
  FLURRIES(5001, Res.string.weather_code_5001, Res.drawable.flurries),
  LIGHT_SNOW(5100, Res.string.weather_code_5100, Res.drawable.snow_light),
  MOSTLY_CLEAR_AND_LIGHT_SNOW(5102, Res.string.weather_code_5102, Res.drawable.snow_light),
  PARTLY_CLOUDY_AND_LIGHT_SNOW(5103, Res.string.weather_code_5103, Res.drawable.snow_light),
  MOSTLY_CLOUDY_AND_LIGHT_SNOW(5104, Res.string.weather_code_5104, Res.drawable.snow_light),
  DRIZZLE_AND_LIGHT_SNOW(5122, Res.string.weather_code_5122, Res.drawable.snow_light),
  MOSTLY_CLEAR_AND_SNOW(5105, Res.string.weather_code_5105, Res.drawable.snow),
  PARTLY_CLOUDY_AND_SNOW(5106, Res.string.weather_code_5106, Res.drawable.snow),
  MOSTLY_CLOUDY_AND_SNOW(5107, Res.string.weather_code_5107, Res.drawable.snow),
  SNOW(5000, Res.string.weather_code_5000, Res.drawable.snow),
  HEAVY_SNOW(5101, Res.string.weather_code_5101, Res.drawable.snow_heavy),
  MOSTLY_CLEAR_AND_HEAVY_SNOW(5119, Res.string.weather_code_5119, Res.drawable.snow_heavy),
  PARTLY_CLOUDY_AND_HEAVY_SNOW(5120, Res.string.weather_code_5120, Res.drawable.snow_heavy),
  MOSTLY_CLOUDY_AND_HEAVY_SNOW(5121, Res.string.weather_code_5121, Res.drawable.snow_heavy),
  DRIZZLE_AND_SNOW(5110, Res.string.weather_code_5110, Res.drawable.snow),
  RAIN_AND_SNOW(5108, Res.string.weather_code_5108, Res.drawable.snow),
  SNOW_AND_FREEZING_RAIN(5114, Res.string.weather_code_5114, Res.drawable.freezing_rain),
  SNOW_AND_ICE_PELLETS(5112, Res.string.weather_code_5112, Res.drawable.ice_pellets),
  FREEZING_DRIZZLE(6000, Res.string.weather_code_6000, Res.drawable.freezing_drizzle),
  MOSTLY_CLEAR_AND_FREEZING_DRIZZLE(6003, Res.string.weather_code_6003, Res.drawable.freezing_drizzle),
  PARTLY_CLOUDY_AND_FREEZING_DRIZZLE(6002, Res.string.weather_code_6002, Res.drawable.freezing_drizzle),
  MOSTLY_CLOUDY_AND_FREEZING_DRIZZLE(6004, Res.string.weather_code_6004, Res.drawable.freezing_drizzle),
  DRIZZLE_AND_FREEZING_DRIZZLE(6204, Res.string.weather_code_6204, Res.drawable.freezing_drizzle),
  LIGHT_RAIN_AND_FREEZING_DRIZZLE(6206, Res.string.weather_code_6206, Res.drawable.freezing_drizzle),
  MOSTLY_CLEAR_AND_LIGHT_FREEZING_RAIN(6205, Res.string.weather_code_6205, Res.drawable.freezing_rain_light),
  PARTLY_CLOUDY_AND_LIGHT_FREEZING_RAIN(6203, Res.string.weather_code_6203, Res.drawable.freezing_rain_light),
  MOSTLY_CLOUDY_AND_LIGHT_FREEZING_RAIN(6209, Res.string.weather_code_6209, Res.drawable.freezing_rain_light),
  LIGHT_FREEZING_RAIN(6200, Res.string.weather_code_6200, Res.drawable.freezing_rain_light),
  MOSTLY_CLEAR_AND_FREEZING_RAIN(6213, Res.string.weather_code_6213, Res.drawable.freezing_rain),
  PARTLY_CLOUDY_AND_FREEZING_RAIN(6214, Res.string.weather_code_6214, Res.drawable.freezing_rain),
  MOSTLY_CLOUDY_AND_FREEZING_RAIN(6215, Res.string.weather_code_6215, Res.drawable.freezing_rain),
  FREEZING_RAIN(6001, Res.string.weather_code_6001, Res.drawable.freezing_rain),
  DRIZZLE_AND_FREEZING_RAIN(6212, Res.string.weather_code_6212, Res.drawable.freezing_rain),
  LIGHT_RAIN_AND_FREEZING_RAIN(6220, Res.string.weather_code_6220, Res.drawable.freezing_rain),
  RAIN_AND_FREEZING_RAIN(6222, Res.string.weather_code_6222, Res.drawable.freezing_rain),
  MOSTLY_CLEAR_AND_HEAVY_FREEZING_RAIN(6207, Res.string.weather_code_6207, Res.drawable.freezing_rain_heavy),
  PARTLY_CLOUDY_AND_HEAVY_FREEZING_RAIN(6202, Res.string.weather_code_6202, Res.drawable.freezing_rain_heavy),
  MOSTLY_CLOUDY_AND_HEAVY_FREEZING_RAIN(6208, Res.string.weather_code_6208, Res.drawable.freezing_rain_heavy),
  HEAVY_FREEZING_RAIN(6201, Res.string.weather_code_6201, Res.drawable.freezing_rain_heavy),
  MOSTLY_CLEAR_AND_LIGHT_ICE_PELLETS(7110, Res.string.weather_code_7110, Res.drawable.ice_pellets_light),
  PARTLY_CLOUDY_AND_LIGHT_ICE_PELLETS(7111, Res.string.weather_code_7111, Res.drawable.ice_pellets_light),
  MOSTLY_CLOUDY_AND_LIGHT_ICE_PELLETS(7112, Res.string.weather_code_7112, Res.drawable.ice_pellets_light),
  LIGHT_ICE_PELLETS(7102, Res.string.weather_code_7102, Res.drawable.ice_pellets_light),
  MOSTLY_CLEAR_AND_ICE_PELLETS(7108, Res.string.weather_code_7108, Res.drawable.ice_pellets),
  PARTLY_CLOUDY_AND_ICE_PELLETS(7107, Res.string.weather_code_7107, Res.drawable.ice_pellets),
  MOSTLY_CLOUDY_AND_ICE_PELLETS(7109, Res.string.weather_code_7109, Res.drawable.ice_pellets),
  ICE_PELLETS(7000, Res.string.weather_code_7000, Res.drawable.ice_pellets),
  DRIZZLE_AND_ICE_PELLETS(7105, Res.string.weather_code_7105, Res.drawable.ice_pellets),
  FREEZING_RAIN_AND_ICE_PELLETS(7106, Res.string.weather_code_7106, Res.drawable.ice_pellets),
  LIGHT_RAIN_AND_ICE_PELLETS(7115, Res.string.weather_code_7115, Res.drawable.ice_pellets),
  RAIN_AND_ICE_PELLETS(7117, Res.string.weather_code_7117, Res.drawable.ice_pellets),
  FREEZING_RAIN_AND_HEAVY_ICE_PELLETS(7103, Res.string.weather_code_7103, Res.drawable.ice_pellets_heavy),
  MOSTLY_CLEAR_AND_HEAVY_ICE_PELLETS(7113, Res.string.weather_code_7113, Res.drawable.ice_pellets_heavy),
  PARTLY_CLOUDY_AND_HEAVY_ICE_PELLETS(7114, Res.string.weather_code_7114, Res.drawable.ice_pellets_heavy),
  MOSTLY_CLOUDY_AND_HEAVY_ICE_PELLETS(7116, Res.string.weather_code_7116, Res.drawable.ice_pellets_heavy),
  HEAVY_ICE_PELLETS(7101, Res.string.weather_code_7101, Res.drawable.ice_pellets_heavy),
  MOSTLY_CLEAR_AND_THUNDERSTORM(8001, Res.string.weather_code_8001, Res.drawable.tstorm),
  PARTLY_CLOUDY_AND_THUNDERSTORM(8003, Res.string.weather_code_8003, Res.drawable.tstorm),
  MOSTLY_CLOUDY_AND_THUNDERSTORM(8002, Res.string.weather_code_8002, Res.drawable.tstorm),
  THUNDERSTORM(8000, Res.string.weather_code_8000, Res.drawable.tstorm),
  ;

  companion object {
    fun fromCode(code: Int): WeatherCode {
      return entries.find { it.code == code } ?: UNKNOWN
    }

    fun getTitle(code: Int): UiString =
      entries.find { it.code == code }?.let { UiString.Resource(it.conditionStringRes) } ?: UiString.Empty
  }
}
