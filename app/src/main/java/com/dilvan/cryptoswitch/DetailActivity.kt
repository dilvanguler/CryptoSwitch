package com.dilvan.cryptoswitch

import ExchangeRateResponse
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.dilvan.cryptoswitch.endpoint.ApiClient
import com.dilvan.cryptoswitch.endpoint.Crypto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // Retrieve the data from the intent
        val crypto = intent.getParcelableExtra<Crypto>("crypto")

        val cryptoName = crypto?.symbol // replace "symbol" with your actual property
        val exchangeRate = crypto?.lastPrice // replace "lastPrice" with your actual property

        val nameTextView = findViewById<TextView>(R.id.tvdBaseAsset) // replace with your actual TextView ID
        nameTextView.text = cryptoName

        // Fetching the exchange rates
        fetchExchangeRates()
    }

    private fun fetchExchangeRates() {
        val currencies = listOf("USD", "SEK", "INR")
        val textViews = listOf(R.id.usd, R.id.sek, R.id.inr)

        // Network request to fetch the exchange rates listed in currencies
        for (i in currencies.indices) {
            val call = ApiClient.exchangeService.getExchangeRate(currencies[i])
            call.enqueue(object : Callback<ExchangeRateResponse> {
                override fun onResponse(call: Call<ExchangeRateResponse>, response: Response<ExchangeRateResponse>) {
                    if (response.isSuccessful) {
                        val rates = response.body()?.rates
                        val rate = when (currencies[i]) {
                            "USD" -> rates?.USD
                            "SEK" -> rates?.SEK
                            "INR" -> rates?.INR
                            else -> null
                        }
                        val textView = findViewById<TextView>(textViews[i])
                        textView.text = rate.toString()
                    }
                }
                override fun onFailure(call: Call<ExchangeRateResponse>, t: Throwable) {
                    // Handle the error
                }
            })
        }
    }
}