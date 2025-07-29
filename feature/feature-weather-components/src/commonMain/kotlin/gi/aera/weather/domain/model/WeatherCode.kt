package gi.aera.weather.domain.model

import gi.aera.weather.Res
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
import org.jetbrains.compose.resources.StringResource

@Suppress("MagicNumber")
enum class WeatherCode(val code: Int, val conditionStringRes: StringResource, val conditionIcon: String = "") {
  UNKNOWN(0, Res.string.weather_code_0),
  CLEAR_SUNNY(1000, Res.string.weather_code_1000, "clear_day"),
  MOSTLY_CLEAR(1100, Res.string.weather_code_1100, "mostly_clear_day"),
  PARTLY_CLOUDY(1101, Res.string.weather_code_1101, "partly_cloudy_day"),
  MOSTLY_CLOUDY(1102, Res.string.weather_code_1102, "most_cloudy_day"),
  CLOUDY(1001, Res.string.weather_code_1001, "cloudy"),
  PARTLY_CLOUDY_AND_MOSTLY_CLEAR(1103, Res.string.weather_code_1103, "partly_cloudy_day"),
  LIGHT_FOG(2100, Res.string.weather_code_2100, "fog"),
  MOSTLY_CLEAR_AND_LIGHT_FOG(2101, Res.string.weather_code_2101, "fog_light"),
  PARTLY_CLOUDY_AND_LIGHT_FOG(2102, Res.string.weather_code_2102, "fog_light"),
  MOSTLY_CLOUDY_AND_LIGHT_FOG(2103, Res.string.weather_code_2103, "fog_light"),
  MOSTLY_CLEAR_AND_FOG(2106, Res.string.weather_code_2106, "fog"),
  PARTLY_CLOUDY_AND_FOG(2107, Res.string.weather_code_2107, "fog"),
  MOSTLY_CLOUDY_AND_FOG(2108, Res.string.weather_code_2108, "fog"),
  FOG(2000, Res.string.weather_code_2000, "fog"),
  PARTLY_CLOUDY_AND_DRIZZLE(4204, Res.string.weather_code_4204, "drizzle"),
  MOSTLY_CLEAR_AND_DRIZZLE(4203, Res.string.weather_code_4203, "drizzle"),
  MOSTLY_CLOUDY_AND_DRIZZLE(4205, Res.string.weather_code_4205, "drizzle"),
  DRIZZLE(4000, Res.string.weather_code_4000, "drizzle"),
  LIGHT_RAIN(4200, Res.string.weather_code_4200, "rain_light"),
  MOSTLY_CLEAR_AND_LIGHT_RAIN(4213, Res.string.weather_code_4213, "rain_light"),
  PARTLY_CLOUDY_AND_LIGHT_RAIN(4214, Res.string.weather_code_4214, "rain_light"),
  MOSTLY_CLOUDY_AND_LIGHT_RAIN(4215, Res.string.weather_code_4215, "rain_light"),
  MOSTLY_CLEAR_AND_RAIN(4209, Res.string.weather_code_4209, "rain"),
  PARTLY_CLOUDY_AND_RAIN(4208, Res.string.weather_code_4208, "rain"),
  MOSTLY_CLOUDY_AND_RAIN(4210, Res.string.weather_code_4210, "rain"),
  RAIN(4001, Res.string.weather_code_4001, "rain"),
  MOSTLY_CLEAR_AND_HEAVY_RAIN(4211, Res.string.weather_code_4211, "rain_heavy"),
  PARTLY_CLOUDY_AND_HEAVY_RAIN(4202, Res.string.weather_code_4202, "rain_heavy"),
  MOSTLY_CLOUDY_AND_HEAVY_RAIN(4212, Res.string.weather_code_4212, "rain_heavy"),
  HEAVY_RAIN(4201, Res.string.weather_code_4201, "rain_heavy"),
  MOSTLY_CLEAR_AND_FLURRIES(5115, Res.string.weather_code_5115, "flurries"),
  PARTLY_CLOUDY_AND_FLURRIES(5116, Res.string.weather_code_5116, "flurries"),
  MOSTLY_CLOUDY_AND_FLURRIES(5117, Res.string.weather_code_5117, "flurries"),
  FLURRIES(5001, Res.string.weather_code_5001, "flurries"),
  LIGHT_SNOW(5100, Res.string.weather_code_5100, "snow_light"),
  MOSTLY_CLEAR_AND_LIGHT_SNOW(5102, Res.string.weather_code_5102, "snow_light"),
  PARTLY_CLOUDY_AND_LIGHT_SNOW(5103, Res.string.weather_code_5103, "snow_light"),
  MOSTLY_CLOUDY_AND_LIGHT_SNOW(5104, Res.string.weather_code_5104, "snow_light"),
  DRIZZLE_AND_LIGHT_SNOW(5122, Res.string.weather_code_5122, "snow_light"),
  MOSTLY_CLEAR_AND_SNOW(5105, Res.string.weather_code_5105, "snow"),
  PARTLY_CLOUDY_AND_SNOW(5106, Res.string.weather_code_5106, "snow"),
  MOSTLY_CLOUDY_AND_SNOW(5107, Res.string.weather_code_5107, "snow"),
  SNOW(5000, Res.string.weather_code_5000, "snow"),
  HEAVY_SNOW(5101, Res.string.weather_code_5101, "heavy_snow"),
  MOSTLY_CLEAR_AND_HEAVY_SNOW(5119, Res.string.weather_code_5119, "heavy_snow"),
  PARTLY_CLOUDY_AND_HEAVY_SNOW(5120, Res.string.weather_code_5120, "heavy_snow"),
  MOSTLY_CLOUDY_AND_HEAVY_SNOW(5121, Res.string.weather_code_5121, "heavy_snow"),
  DRIZZLE_AND_SNOW(5110, Res.string.weather_code_5110, "snow"),
  RAIN_AND_SNOW(5108, Res.string.weather_code_5108, "snow"),
  SNOW_AND_FREEZING_RAIN(5114, Res.string.weather_code_5114, "freezing_rain"),
  SNOW_AND_ICE_PELLETS(5112, Res.string.weather_code_5112, "ice_pellets"),
  FREEZING_DRIZZLE(6000, Res.string.weather_code_6000, "freezing_drizzle"),
  MOSTLY_CLEAR_AND_FREEZING_DRIZZLE(6003, Res.string.weather_code_6003, "freezing_drizzle"),
  PARTLY_CLOUDY_AND_FREEZING_DRIZZLE(6002, Res.string.weather_code_6002, "freezing_drizzle"),
  MOSTLY_CLOUDY_AND_FREEZING_DRIZZLE(6004, Res.string.weather_code_6004, "freezing_drizzle"),
  DRIZZLE_AND_FREEZING_DRIZZLE(6204, Res.string.weather_code_6204, "freezing_drizzle"),
  LIGHT_RAIN_AND_FREEZING_DRIZZLE(6206, Res.string.weather_code_6206, "freezing_drizzle"),
  MOSTLY_CLEAR_AND_LIGHT_FREEZING_RAIN(6205, Res.string.weather_code_6205, "freezing_rain_light"),
  PARTLY_CLOUDY_AND_LIGHT_FREEZING_RAIN(6203, Res.string.weather_code_6203, "freezing_rain_light"),
  MOSTLY_CLOUDY_AND_LIGHT_FREEZING_RAIN(6209, Res.string.weather_code_6209, "freezing_rain_light"),
  LIGHT_FREEZING_RAIN(6200, Res.string.weather_code_6200, "freezing_rain_light"),
  MOSTLY_CLEAR_AND_FREEZING_RAIN(6213, Res.string.weather_code_6213, "freezing_rain"),
  PARTLY_CLOUDY_AND_FREEZING_RAIN(6214, Res.string.weather_code_6214, "freezing_rain"),
  MOSTLY_CLOUDY_AND_FREEZING_RAIN(6215, Res.string.weather_code_6215, "freezing_rain"),
  FREEZING_RAIN(6001, Res.string.weather_code_6001, "freezing_rain"),
  DRIZZLE_AND_FREEZING_RAIN(6212, Res.string.weather_code_6212, "freezing_rain"),
  LIGHT_RAIN_AND_FREEZING_RAIN(6220, Res.string.weather_code_6220, "freezing_rain"),
  RAIN_AND_FREEZING_RAIN(6222, Res.string.weather_code_6222, "freezing_rain"),
  MOSTLY_CLEAR_AND_HEAVY_FREEZING_RAIN(6207, Res.string.weather_code_6207, "freezing_rain_heavy"),
  PARTLY_CLOUDY_AND_HEAVY_FREEZING_RAIN(6202, Res.string.weather_code_6202, "freezing_rain_heavy"),
  MOSTLY_CLOUDY_AND_HEAVY_FREEZING_RAIN(6208, Res.string.weather_code_6208, "freezing_rain_heavy"),
  HEAVY_FREEZING_RAIN(6201, Res.string.weather_code_6201, "freezing_rain_heavy"),
  MOSTLY_CLEAR_AND_LIGHT_ICE_PELLETS(7110, Res.string.weather_code_7110, "ice_pellets_light"),
  PARTLY_CLOUDY_AND_LIGHT_ICE_PELLETS(7111, Res.string.weather_code_7111, "ice_pellets_light"),
  MOSTLY_CLOUDY_AND_LIGHT_ICE_PELLETS(7112, Res.string.weather_code_7112, "ice_pellets_light"),
  LIGHT_ICE_PELLETS(7102, Res.string.weather_code_7102, "ice_pellets_light"),
  MOSTLY_CLEAR_AND_ICE_PELLETS(7108, Res.string.weather_code_7108, "ice_pellets"),
  PARTLY_CLOUDY_AND_ICE_PELLETS(7107, Res.string.weather_code_7107, "ice_pellets"),
  MOSTLY_CLOUDY_AND_ICE_PELLETS(7109, Res.string.weather_code_7109, "ice_pellets"),
  ICE_PELLETS(7000, Res.string.weather_code_7000, "ice_pellets"),
  DRIZZLE_AND_ICE_PELLETS(7105, Res.string.weather_code_7105, "ice_pellets"),
  FREEZING_RAIN_AND_ICE_PELLETS(7106, Res.string.weather_code_7106, "ice_pellets"),
  LIGHT_RAIN_AND_ICE_PELLETS(7115, Res.string.weather_code_7115, "ice_pellets"),
  RAIN_AND_ICE_PELLETS(7117, Res.string.weather_code_7117, "ice_pellets"),
  FREEZING_RAIN_AND_HEAVY_ICE_PELLETS(7103, Res.string.weather_code_7103, "ice_pellets_heavy"),
  MOSTLY_CLEAR_AND_HEAVY_ICE_PELLETS(7113, Res.string.weather_code_7113, "ice_pellets_heavy"),
  PARTLY_CLOUDY_AND_HEAVY_ICE_PELLETS(7114, Res.string.weather_code_7114, "ice_pellets_heavy"),
  MOSTLY_CLOUDY_AND_HEAVY_ICE_PELLETS(7116, Res.string.weather_code_7116, "ice_pellets_heavy"),
  HEAVY_ICE_PELLETS(7101, Res.string.weather_code_7101, "ice_pellets_heavy"),
  MOSTLY_CLEAR_AND_THUNDERSTORM(8001, Res.string.weather_code_8001, "tstorm"),
  PARTLY_CLOUDY_AND_THUNDERSTORM(8003, Res.string.weather_code_8003, "tstorm"),
  MOSTLY_CLOUDY_AND_THUNDERSTORM(8002, Res.string.weather_code_8002, "tstorm"),
  THUNDERSTORM(8000, Res.string.weather_code_8000, "tstorm"),
  ;

  companion object {
    fun fromCode(code: Int): WeatherCode {
      return values().find { it.code == code } ?: UNKNOWN
    }
  }
}
