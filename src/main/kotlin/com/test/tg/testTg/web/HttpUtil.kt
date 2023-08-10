package com.test.tg.testTg.web

import com.google.gson.GsonBuilder
import com.test.tg.testTg.utils.Constants.WEATHER_BASE_URL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.slf4j.LoggerFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object HttpUtil {

    private val log = LoggerFactory.getLogger(HttpUtil::class.java)

    private val interceptor: Interceptor = HttpLoggingInterceptor(log::info).setLevel(HttpLoggingInterceptor.Level.BODY)

    private val gson = GsonBuilder().setLenient().serializeNulls().create()

    private fun createOkHttpClient() =
        OkHttpClient.Builder().apply {
            addInterceptor(interceptor)
        }.build()

    fun createRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(WEATHER_BASE_URL)
        .client(createOkHttpClient())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
}