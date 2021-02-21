package com.okellosoftwarez.currencyconverter.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.okellosoftwarez.currencyconverter.data.models.Rates
import com.okellosoftwarez.currencyconverter.util.DispatcherProvider
import com.okellosoftwarez.currencyconverter.util.resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.math.round

class mainViewModel @ViewModelInject constructor(
  private val repository: mainRepository,
  private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

  sealed class CurrencyEvent {
    class Success(val resultText : String) : CurrencyEvent()
    class Failure(val errorText : String) : CurrencyEvent()
    object Loading : CurrencyEvent()
    object Empty : CurrencyEvent()
  }

  private val _conversion = MutableStateFlow<CurrencyEvent>(CurrencyEvent.Empty)
  val conversion : StateFlow<CurrencyEvent> = _conversion


  fun convert(amountStr : String, fromCur : String, toCurr : String) {
    val fromAmount = amountStr.toFloatOrNull();
    if (fromAmount == null){
      _conversion.value = CurrencyEvent.Failure("Not a valid Amount")
      return
    }

    viewModelScope.launch(dispatcherProvider.io) {
      _conversion.value = CurrencyEvent.Loading
      when(val ratesResponse = repository.getRates(fromCur)){
        is resource.Error -> _conversion.value = CurrencyEvent.Failure(ratesResponse.message!!)
        is resource.Success -> {
          val rates = ratesResponse.data!!.rates
          val rate = getRateForCurrency(toCurr, rates)
          if (rate == null){
            _conversion.value  = CurrencyEvent.Failure("Unexpected Error")
          }
          else {
            val convertedCurrency = round(fromAmount * rate * 100) / 100
            _conversion.value = CurrencyEvent.Success(
              "$fromAmount  $fromCur  =  $convertedCurrency  $toCurr"
            )
          }
        }

      }
    }
  }

  private fun getRateForCurrency(currency : String, rates: Rates) = when (currency){

    "CAD" -> rates.CAD
    "HKD" -> rates.HKD
    "ISK" -> rates.ISK
    "PHP" -> rates.PHP
    "DKK" -> rates.DKK
    "HUF" -> rates.HUF
    "CZK" -> rates.CZK
    "AUD" -> rates.AUD
    "RON" -> rates.RON
    "SEK" -> rates.SEK
    "IDR" -> rates.IDR
    "INR" -> rates.INR
    "BRL" -> rates.BRL
    "RUB" -> rates.RUB
    "HRK" -> rates.HRK
    "JPY" -> rates.JPY
    "THB" -> rates.THB
    "CHF" -> rates.CHF
    "SGD" -> rates.SGD
    "PLN" -> rates.PLN
    "BGN" -> rates.BGN
    "TRY" -> rates.TRY
    "CNY" -> rates.CNY
    "NOK" -> rates.NOK
    "NZD" -> rates.NZD
    "ZAR" -> rates.ZAR
    "USD" -> rates.USD
    "MXN" -> rates.MXN
    "ILS" -> rates.ILS
    "GBP" -> rates.GBP
    "KRW" -> rates.KRW
    "MYR" -> rates.MYR
    "EUR" -> rates.EUR
    else -> rates.USD
  }
}