package com.okellosoftwarez.currencyconverter.main

import com.okellosoftwarez.currencyconverter.CurrencyApi
import com.okellosoftwarez.currencyconverter.data.models.currencyResponse
import com.okellosoftwarez.currencyconverter.util.resource
import javax.inject.Inject

class DefaultMainRepository @Inject constructor(
    private val api: CurrencyApi
) : mainRepository {

    override suspend fun getRates(base: String): resource<currencyResponse> {

        return try {
            val response = api.getRates(base)
            val result = response.body()

            if (response.isSuccessful && result != null){
                resource.Success(result)
            }
            else {
                resource.Error(response.message())
            }
        }
        catch (e : Exception){

            resource.Error(e.message ?: "An error Occured")
        }
    }

}