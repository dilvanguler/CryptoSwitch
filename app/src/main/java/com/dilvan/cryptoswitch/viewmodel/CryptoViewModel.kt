package com.dilvan.cryptoswitch.viewmodel

import ExchangeRateResponse
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dilvan.cryptoswitch.data.Crypto
import com.dilvan.cryptoswitch.endpoint.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CryptoViewModel : ViewModel() {
    val cryptoData = MutableLiveData<List<Crypto>>()
    val isLoading = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()
    val exchangeRates = MutableLiveData<Map<String, Double>>()

    fun fetchCryptoData() {
        isLoading.value = true

        val call = ApiClient.cryptoService.getCryptocurrencies()
        call.enqueue(object : Callback<List<Crypto>> {
            override fun onResponse(call: Call<List<Crypto>>, response: Response<List<Crypto>>) {
                isLoading.value = false

                if (response.isSuccessful) {
                    cryptoData.value = response.body()
                } else {
                    // Handle the error
                    error.value = "Error: ${response.errorBody()?.string()}"
                }
            }

            override fun onFailure(call: Call<List<Crypto>>, t: Throwable) {
                isLoading.value = false
                error.value = "Failure: ${t.message}"
            }
        })
    }

    fun fetchExchangeRates(baseCurrency: String) {
        isLoading.value = true

        val call = ApiClient.exchangeService.getExchangeRate(
                "8dd7e35a97d46e859c9ec6e5195b4fa3", "EUR", "USD,SEK,INR")
        call.enqueue(object : Callback<ExchangeRateResponse> {
            override fun onResponse(call: Call<ExchangeRateResponse>, response: Response<ExchangeRateResponse>) {
                isLoading.value = false

                if (response.isSuccessful) {
                    exchangeRates.value = response.body()?.rates
                } else {
                    error.value = "Error: ${response.errorBody()?.string()}"
                }
            }

            override fun onFailure(call: Call<ExchangeRateResponse>, t: Throwable) {
                isLoading.value = false
                error.value = "Failure: ${t.message}"
            }
        })
    }
}
