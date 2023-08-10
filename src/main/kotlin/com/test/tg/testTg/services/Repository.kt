package com.test.tg.testTg.services

import com.test.tg.testTg.web.HttpUtil
import com.test.tg.testTg.web.WeatherApi
import com.test.tg.testTg.web.models.GetGeoRes
import com.test.tg.testTg.web.models.WeatherRes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class Repository(contract: Contract) {

    private val contract: Contract

    init {
        this.contract = contract
    }


    private val retroService = HttpUtil.createRetrofit()
    private val employeeService: WeatherApi by lazy { retroService.create() }

    fun getGeo(chatId: Long, city: String) {
        contract.startLoader(chatId)
        employeeService.getGeoOfCity(city).enqueue(object : Callback<Array<GetGeoRes>> {

            override fun onResponse(call: Call<Array<GetGeoRes>>, response: Response<Array<GetGeoRes>>) {
                if(!response.isSuccessful || response.body() == null){
                    contract.onError(chatId, "Null data")
                    return
                }

                val lat = response.body()!![0].lat
                val lon = response.body()!![0].lon

                getWeather(lat, lon, chatId)
            }

            override fun onFailure(call: Call<Array<GetGeoRes>>, t: Throwable) {
                contract.onError(chatId, t.message.toString())
            }
        })
    }

    private fun getWeather(lat: Double, lon: Double, chatId: Long){
        employeeService.getWeather(lat, lon).enqueue(object: Callback<WeatherRes>{

            override fun onResponse(call: Call<WeatherRes>, response: Response<WeatherRes>) {
                if(!response.isSuccessful || response.body() == null){
                    contract.onError(chatId, "Null data")
                    return
                }

                contract.onSuccessWeather(response.body()!!, chatId)
            }

            override fun onFailure(call: Call<WeatherRes>, t: Throwable) {
                contract.onError(chatId, t.message.toString())
            }
        })
    }
}