data class ExchangeRateResponse(
    val success: Boolean,
    val timestamp: Long,
    val base: String,
    val date: String,
    val rates: Rates
)

data class Rates(
    val USD: Double,
    val SEK: Double,
    val INR: Double
)