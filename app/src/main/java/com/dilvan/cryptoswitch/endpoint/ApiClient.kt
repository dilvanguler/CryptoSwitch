package com.dilvan.cryptoswitch.endpoint

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
object ApiClient {
    private const val CRYPTO_BASE_URL = "https://api.wazirx.com/"
    private const val EXCHANGE_BASE_URL = "http://api.exchangeratesapi.io/"

    private val cryptoRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl(CRYPTO_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val exchangeRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl(EXCHANGE_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val cryptoService: CryptoService = cryptoRetrofit.create(CryptoService::class.java)
    val exchangeService: ExchangeService = exchangeRetrofit.create(ExchangeService::class.java)
}