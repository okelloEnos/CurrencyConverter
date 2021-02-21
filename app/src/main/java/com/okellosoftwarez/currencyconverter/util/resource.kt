package com.okellosoftwarez.currencyconverter.util

sealed class resource <T> (val data : T?, val message : String?) {
    class Success <T> (data: T) : resource <T> (data, null)
    class Error <T> (message: String) : resource <T> (null, message)
}