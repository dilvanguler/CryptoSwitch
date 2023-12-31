package com.dilvan.cryptoswitch.data

import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class Crypto(
    val symbol: String,
    val baseAsset: String,
    val quoteAsset: String,
    val openPrice: String,
    val lowPrice: String,
    val highPrice: String,
    val lastPrice: String,
    val volume: String,
    val bidPrice: String,
    val askPrice: String,
    val at: Long
) : Parcelable