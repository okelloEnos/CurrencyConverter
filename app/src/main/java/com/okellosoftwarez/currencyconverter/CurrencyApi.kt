package com.okellosoftwarez.currencyconverter

import com.okellosoftwarez.currencyconverter.data.models.currencyResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {

    @GET("/latest")
    suspend fun getRates( @Query("base") base: String) : Response<currencyResponse>
}