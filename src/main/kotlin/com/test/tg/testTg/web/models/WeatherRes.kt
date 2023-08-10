package com.test.tg.testTg.web.models

import com.google.gson.annotations.SerializedName

data class WeatherRes(
    val weather: Array<Weather>,
    val main: WeatherMain,
    val wind: WeatherWind,
    val name: String
)

data class Weather(
    val description: String
)

data class WeatherMain(
    val temp: Double,
    @SerializedName("feels_like") val feelsLike: Double,
    val pressure: Double
)

data class WeatherWind(
    val speed: Double
)