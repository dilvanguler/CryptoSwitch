package com.dilvan.cryptoswitch

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dilvan.cryptoswitch.adapter.ExchangeRateAdapter
import com.dilvan.cryptoswitch.data.ExchangeRate
import com.dilvan.cryptoswitch.viewmodel.ExchangeRateViewModel

class ExchangeRateActivity : AppCompatActivity() {
    private lateinit var adapter: ExchangeRateAdapter
    private var exchangeRates: List<ExchangeRate> = listOf()
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        // Initializing a ProgressBar
       progressBar = findViewById(R.id.idExchangeLoading)

        adapter = ExchangeRateAdapter()
        val recyclerView = findViewById<RecyclerView>(R.id.idCurrencyExchange)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter  // Set the adapter here

        val viewModel = ViewModelProvider(this).get(ExchangeRateViewModel::class.java)
        viewModel.exchangeRates.observe(this, Observer { response ->
            val rates = response.rates.map { ExchangeRate(it.key, it.value) }
            exchangeRates = rates
            adapter.setExchangeRates(rates)

            // Hide Progressbar
            progressBar.visibility = View.GONE
        })
        // Show the progress indicator before starting the network call
        progressBar.visibility = View.VISIBLE

        viewModel.fetchExchangeRates()
    }
}