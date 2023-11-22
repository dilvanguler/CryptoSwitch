package com.dilvan.cryptoswitch.adapter

import com.dilvan.cryptoswitch.data.Crypto
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dilvan.cryptoswitch.R

class CryptoAdapter(private val onCryptoClick: (Crypto) -> Unit) :
    RecyclerView.Adapter<CryptoAdapter.CryptoViewHolder>() {

    private var data = listOf<Crypto>()

    fun setData(newData: List<Crypto>) {
        data = newData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.crypto_item, parent, false)
        return CryptoViewHolder(view)
    }

    override fun onBindViewHolder(holder: CryptoViewHolder, position: Int) {
        val crypto = data[position]
        holder.bind(crypto)
        holder.itemView.setOnClickListener {
            onCryptoClick(crypto)
        }
    }

    override fun getItemCount() = data.size

    class CryptoViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(crypto: Crypto) {
            view.findViewById<TextView>(R.id.tvBaseAsset).text = crypto.baseAsset
            view.findViewById<TextView>(R.id.tvQuoteAsset).text = crypto.quoteAsset

        }
    }
}