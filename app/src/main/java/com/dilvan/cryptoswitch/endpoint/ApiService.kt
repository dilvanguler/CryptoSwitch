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
    @GET("v1/latest")
    fun getExchangeRate(
        @Query("access_key") accessKey: String,
        @Query("base") base: String,
        @Query("symbols") symbols: String
    ): Call<ExchangeRateResponse>
}