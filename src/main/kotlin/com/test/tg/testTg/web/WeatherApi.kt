package com.test.tg.testTg.web

import com.test.tg.testTg.utils.Constants.WEATHER_API_KEY
import com.test.tg.testTg.web.models.GetGeoRes
import com.test.tg.testTg.web.models.WeatherRes
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("geo/1.0/direct?appid=$WEATHER_API_KEY")
    fun getGeoOfCity(@Query("q") city: String): Call<Array<GetGeoRes>>

    @GET("https://api.openweathermap.org/data/2.5/weather?appid=$WEATHER_API_KEY&lang=ru&units=metric")
    fun getWeather(@Query("lat") lat: Double, @Query("lon") lon: Double): Call<WeatherRes>
}