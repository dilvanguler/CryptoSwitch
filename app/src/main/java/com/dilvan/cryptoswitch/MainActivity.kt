package com.dilvan.cryptoswitch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dilvan.cryptoswitch.endpoint.Crypto
import com.dilvan.cryptoswitch.endpoint.CryptoViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var adapter: CryptoAdapter
    private var cryptocurrencies: List<Crypto> = listOf()
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = CryptoAdapter { crypto ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("crypto", crypto)
            startActivity(intent)
        }

        val recyclerView = findViewById<RecyclerView>(R.id.idCurrencies)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        progressBar = findViewById(R.id.idLoading)

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

        val viewModel = ViewModelProvider(this).get(CryptoViewModel::class.java)
        viewModel.fetchCryptoData()

        viewModel.cryptoData.observe(this, Observer { cryptoList ->
            // Update your RecyclerView with the new data
            adapter.setData(cryptoList)

            // Update your list of cryptocurrencies
            cryptocurrencies = cryptoList
        })

        viewModel.isLoading.observe(this, Observer { isLoading ->
            // Show or hide your ProgressBar based on the value of isLoading
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        })

        viewModel.error.observe(this, Observer { error ->
            // Show the error message
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        })
    }

}