package com.okellosoftwarez.currencyconverter.main

import com.okellosoftwarez.currencyconverter.data.models.currencyResponse
import com.okellosoftwarez.currencyconverter.util.resource

interface mainRepository {

    suspend fun getRates(base : String) : resource <currencyResponse>
}