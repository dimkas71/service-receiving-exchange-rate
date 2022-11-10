package ua.dimkas71

import kotlinx.serialization.Serializable

@Serializable
data class CurrencyRate(val ccy: String, val base_ccy: String, val buy: Float, val sell: Float, val date: String)

enum class CurrencyRateType {
    NBU,
    AVG,
    BLACK
}
