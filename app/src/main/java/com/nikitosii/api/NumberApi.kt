package com.nikitosii.api

import com.nikitosii.core.database.entity.NumberFact
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface NumberApi {
    @Headers("Content-Type: application/json")
    @GET("/{number}")
    suspend fun getFact(@Path("number") id: Int): NumberFact

    @Headers("Content-Type: application/json")
    @GET("/random/math")
    suspend fun getRandomNumber(): NumberFact
}