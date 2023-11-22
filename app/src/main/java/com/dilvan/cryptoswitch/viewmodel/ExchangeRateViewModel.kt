package com.dilvan.cryptoswitch.viewmodel

import ExchangeRateResponse
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dilvan.cryptoswitch.endpoint.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExchangeRateViewModel : ViewModel() {
    val exchangeRates = MutableLiveData<ExchangeRateResponse>()

    fun fetchExchangeRates() {
        val call = ApiClient.exchangeService.getExchangeRate(
            "8dd7e35a97d46e859c9ec6e5195b4fa3", "EUR", "USD,SEK,INR")
        call.enqueue(object : Callback<ExchangeRateResponse> {
            override fun onResponse(
                call: Call<ExchangeRateResponse>,
                response: Response<ExchangeRateResponse>)
            {
                if (response.isSuccessful) {
                    exchangeRates.value = response.body()
                } else {
                    Log.e("ExchangeRateViewModel", "Error: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<ExchangeRateResponse>, t: Throwable) {
                Log.e("ExchangeRateViewModel", "Failure: ${t.message}")
            }
        })
    }
}