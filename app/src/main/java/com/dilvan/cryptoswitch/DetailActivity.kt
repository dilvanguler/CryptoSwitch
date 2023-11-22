package com.dilvan.cryptoswitch

import ExchangeRateResponse
import android.os.Bundle
import android.util.Log
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

        val cryptoName = crypto?.baseAsset // replace "symbol" with your actual property
        val price = crypto?.openPrice // replace "lastPrice" with your actual property

        val quoteAsset = crypto?.quoteAsset

        val nameTextView =
            findViewById<TextView>(R.id.tvdBaseAsset) // replace with your actual TextView ID
        nameTextView.text = cryptoName

        val priceTextView = findViewById<TextView>(R.id.tvdPrice)
        priceTextView.text = "Price: $price"

        val quoteAssetTextView = findViewById<TextView>(R.id.tvdQuoteAsset)
        quoteAssetTextView.text = "Currency:  $quoteAsset"

        // Fetching the exchange rates
        fetchExchangeRates()
    }

    private fun fetchExchangeRates() {
        val call = ApiClient.exchangeService.getExchangeRate(
            "8dd7e35a97d46e859c9ec6e5195b4fa3", "EUR", "USD,SEK,INR")
        call.enqueue(object : Callback<ExchangeRateResponse> {
            override fun onResponse(call: Call<ExchangeRateResponse>, response: Response<ExchangeRateResponse>) {
                if (response.isSuccessful) {
                    val rates = response.body()?.rates
                    // Log the rates or update your UI here
                    findViewById<TextView>(R.id.usd).text = "USD: ${rates?.USD}"
                    findViewById<TextView>(R.id.sek).text = "SEK: ${rates?.SEK}"
                    findViewById<TextView>(R.id.inr).text = "INR: ${rates?.INR}"
                } else {
                    val errorMessage = response.errorBody()?.string()
                    Log.e("DetailActivity", "Failed to fetch exchange rates: $errorMessage")
                }
            }

            override fun onFailure(call: Call<ExchangeRateResponse>, t: Throwable) {
                Log.e("DetailActivity", "Network request failed", t)
            }
        })
    }
}
