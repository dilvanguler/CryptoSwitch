package com.dilvan.cryptoswitch.viewmodel

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
}
