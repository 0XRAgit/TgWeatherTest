package com.test.tg.testTg.web.models

import com.google.gson.annotations.SerializedName

data class GetGeoRes(
    @SerializedName("local_names") val localNames: LocalNames,
    val lat: Double,
    val lon: Double
)

data class LocalNames(
    val lat: Double,
    val lon: Double
)