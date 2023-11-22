package com.dilvan.cryptoswitch

import ExchangeRateResponse
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.dilvan.cryptoswitch.endpoint.ApiClient
import com.dilvan.cryptoswitch.data.Crypto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {
    private val rates = mutableMapOf<String, Double>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // Initialize the spinner and currencies here
        val spinner = findViewById<Spinner>(R.id.spinner)
        val currencies = arrayOf("USD", "SEK", "INR", "BTC", "USDT", "WRX")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencies)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        // Retrieve the data from the intent
        val crypto = intent.getParcelableExtra<Crypto>("crypto")

        val cryptoName = crypto?.baseAsset
        val price = crypto?.openPrice

        val quoteAsset = crypto?.quoteAsset

        val nameTextView = findViewById<TextView>(R.id.tvdBaseAsset)
        nameTextView.text = cryptoName

        val priceTextView = findViewById<TextView>(R.id.tvdPrice)
        priceTextView.text = "Price: $price"

        val quoteAssetTextView = findViewById<TextView>(R.id.tvdQuoteAsset)
        quoteAssetTextView.text = "Currency:  $quoteAsset"

        // Fetching the exchange rates
        fetchExchangeRates(spinner, currencies, priceTextView, intent, rates)
    }
    }

private fun fetchExchangeRates(
    spinner: Spinner,
    currencies: Array<String>,
    priceTextView: TextView,
    intent: Intent,
    rates: MutableMap<String, Double>
)
{
    val crypto = intent.getParcelableExtra<Crypto>("crypto")
    val baseCurrency = crypto?.quoteAsset?.toUpperCase() ?: "EUR" // Default to EUR if quoteAsset is null

    // Hardcoded exchange rates as chosen API does not provide these, in a time-crunch. TODO(dilvan):
    val hardcodedRates = mapOf(
        "BTC" to 33497.60,
        "USDT" to 0.92,
        "WRX" to 0.11)

    val call = ApiClient.exchangeService.getExchangeRate(
        "8dd7e35a97d46e859c9ec6e5195b4fa3", "EUR", "USD,SEK,INR")
    call.enqueue(object : Callback<ExchangeRateResponse> {
        override fun onResponse(
            call: Call<ExchangeRateResponse>,
            response: Response<ExchangeRateResponse>)
        {
            if (response.isSuccessful) {
                val ratesResponse = response.body()?.rates
                rates.clear()
                ratesResponse?.let {
                    rates["USD"] = it.USD
                    rates["SEK"] = it.SEK
                    rates["INR"] = it.INR
                }
                rates.putAll(hardcodedRates)



                // Set up the onItemSelectedListener here
                spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View,
                        position: Int,
                        id: Long) {

                        val selectedCurrency = currencies[position]
                        val rate = rates[selectedCurrency]
                        val price = crypto?.openPrice?.toDouble() ?: 0.0
                        val convertedPrice = price * (rate ?: 1.0)


                            priceTextView.text = "Price: $convertedPrice $selectedCurrency"
                    }

                    override fun onNothingSelected(parent: AdapterView<*>) {
                        // Do nothing
                    }
                }
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

