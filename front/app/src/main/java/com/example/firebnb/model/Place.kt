package com.example.firebnb.model

@JsonClass(generate_adapter = true)
data class Place(
    val id: Int,
    val owner_id: Int,
    val name: String,
    val type: String,
    val description: String,
    val price_per_night: Float,
    val stars: Int
)
