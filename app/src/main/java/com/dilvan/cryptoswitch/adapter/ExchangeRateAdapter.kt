package com.dilvan.cryptoswitch.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dilvan.cryptoswitch.R
import com.dilvan.cryptoswitch.data.ExchangeRate

class ExchangeRateAdapter : RecyclerView.Adapter<ExchangeRateAdapter.ViewHolder>() {
    private var exchangeRates = listOf<ExchangeRate>()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val currencyTextView: TextView = view.findViewById(R.id.currencyTextView)
        val rateTextView: TextView = view.findViewById(R.id.rateTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.exchange_rates, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val exchangeRate = exchangeRates[position]
        holder.currencyTextView.text = exchangeRate.currency
        holder.rateTextView.text = exchangeRate.rate.toString()
    }

    override fun getItemCount() = exchangeRates.size

    fun setExchangeRates(rates: List<ExchangeRate>) {
        exchangeRates = rates
        notifyDataSetChanged()
    }
}