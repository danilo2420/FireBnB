package com.example.firebnb.model.api.responses

import com.example.firebnb.model.Renting
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RentingList(
    val rentings: List<Renting>
)
