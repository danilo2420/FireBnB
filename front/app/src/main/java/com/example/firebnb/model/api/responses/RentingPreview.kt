package com.example.firebnb.model.api.responses

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RentingPreview(
    val place_id: Int,
    val name: String,
    val type: String,
    val start_date: String,
    val end_date: String,
    val total_price: Float
)
