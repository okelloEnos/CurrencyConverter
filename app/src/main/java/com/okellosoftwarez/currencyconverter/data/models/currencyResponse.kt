package com.okellosoftwarez.currencyconverter.data.models

data class currencyResponse(
    val base: String,
    val date: String,
    val rates: Rates
)