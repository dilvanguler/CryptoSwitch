package com.dilvan.cryptoswitch.endpoint

import ExchangeRateResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CryptoService {
    @GET("sapi/v1/tickers/24hr")
    fun getCryptocurrencies(): Call<List<Crypto>>
}

interface ExchangeService {
    @GET("v1/latest?access_key=8dd7e35a97d46e859c9ec6e5195b4fa3")
    fun getExchangeRate(@Query("base") base: String): Call<ExchangeRateResponse>
}