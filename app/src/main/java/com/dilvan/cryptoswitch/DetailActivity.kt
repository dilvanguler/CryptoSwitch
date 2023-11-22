package com.dilvan.cryptoswitch

import ExchangeRateResponse
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button

import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dilvan.cryptoswitch.endpoint.ApiClient
import com.dilvan.cryptoswitch.data.Crypto
import com.dilvan.cryptoswitch.viewmodel.CryptoViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {
    private lateinit var viewModel: CryptoViewModel
    private val rates = mutableMapOf<String, Double>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        viewModel = ViewModelProvider(this).get(CryptoViewModel::class.java)

        // Retrieve the data from the intent
        val crypto = intent.getParcelableExtra<Crypto>("crypto")
        val cryptoName = crypto?.baseAsset
        val nameTextView = findViewById<TextView>(R.id.tvdBaseAsset)
        if (cryptoName != null) {
            nameTextView.text = cryptoName.toUpperCase()
        }
        val spinner = findViewById<Spinner>(R.id.spinner)
        val priceTextView = findViewById<TextView>(R.id.tvdPrice)
        val currencies = arrayOf("USD", "SEK", "INR")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencies)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        // Button to next activity
        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            val intent = Intent(this, ExchangeRateActivity::class.java)
            startActivity(intent)
        }

        viewModel.exchangeRates.observe(this) { rates ->
            val selectedCurrency = spinner.selectedItem.toString()
            val rate = rates[selectedCurrency]
            if (rate != null) {
                val priceInUSD = rate * 1 // Replace 1 with the price in the selected currency
                priceTextView.text = "Price in $selectedCurrency: $priceInUSD"
            }
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedCurrency = parent.getItemAtPosition(position).toString()
                viewModel.fetchExchangeRates(selectedCurrency)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }
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

    val call = ApiClient.exchangeService.getExchangeRate(
        "8dd7e35a97d46e859c9ec6e5195b4fa3", "EUR", "USD,SEK,INR")
    call.enqueue(object : Callback<ExchangeRateResponse> {
        override fun onResponse(
            call: Call<ExchangeRateResponse>,
            response: Response<ExchangeRateResponse>)
        {
            if (response.isSuccessful) {
                val exchangeRateResponse = response.body()
                rates.clear()
                exchangeRateResponse?.let {
                    rates["USD"] = it.rates["USD"] ?: 0.0
                    rates["SEK"] = it.rates["SEK"] ?: 0.0
                    rates["INR"] = it.rates["INR"] ?: 0.0
                }

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

