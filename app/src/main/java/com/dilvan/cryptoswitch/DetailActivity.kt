package com.dilvan.cryptoswitch

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // Retrieve the data from the intent
        val cryptoName = intent.getStringExtra("crypto_name") // replace "crypto_name" with your actual key
        val exchangeRate = intent.getStringExtra("exchange_rate") // replace "exchange_rate" with your actual key

        // Find the TextViews and set their text to the data
   //     val nameTextView = findViewById<TextView>(R.id.name_text_view) // replace with your actual TextView ID
   //     nameTextView.text = cryptoName

    //    val rateTextView = findViewById<TextView>(R.id.rate_text_view) // replace with your actual TextView ID
    //    rateTextView.text = exchangeRate
    }
}