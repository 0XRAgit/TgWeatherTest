package com.test.tg.testTg.services

import com.test.tg.testTg.web.models.WeatherRes

interface Contract {
    fun onError(chatId: Long, error: String)
    fun onSuccessWeather(data: WeatherRes, chatId: Long)
    fun startLoader(chatId:Long)
}