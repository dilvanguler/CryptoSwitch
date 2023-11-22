package com.dilvan.cryptoswitch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dilvan.cryptoswitch.endpoint.ApiClient
import com.dilvan.cryptoswitch.endpoint.Crypto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: CryptoAdapter
    private var cryptocurrencies: List<Crypto> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = CryptoAdapter()
        val recyclerView = findViewById<RecyclerView>(R.id.idCurrencies)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val searchEditText = findViewById<EditText>(R.id.idSearch)
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable) {
                val searchText = s.toString()
                val filteredCryptocurrencies = cryptocurrencies.filter { it.symbol.contains(searchText, ignoreCase = true) }
                adapter.setData(filteredCryptocurrencies)
            }
        })

        fetchCryptoData()
    }

    private fun fetchCryptoData() {
        val progressBar = findViewById<ProgressBar>(R.id.idLoading)
        progressBar.visibility = View.VISIBLE

        val call = ApiClient.cryptoService.getCryptocurrencies()
        call.enqueue(object : Callback<List<Crypto>> {
            override fun onResponse(call: Call<List<Crypto>>, response: Response<List<Crypto>>) {
                progressBar.visibility = View.GONE

                if (response.isSuccessful) {
                    cryptocurrencies = response.body() ?: listOf()
                    adapter.setData(cryptocurrencies)
                } else {
                    // Handle the error
                }
            }

            override fun onFailure(call: Call<List<Crypto>>, t: Throwable) {
                progressBar.visibility = View.GONE
                // Handle the failure
            }
        })
    }
}