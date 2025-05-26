package com.example.firebnb.model

import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class Renting(
    val id: Int,
    val place_id: Int,
    val guest_id: Int,
    val start_date: String,
    val end_date: String,
    val total_price: Float
): Serializable