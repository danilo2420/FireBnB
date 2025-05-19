package com.example.firebnb.model.api.responses

import com.example.firebnb.model.Place
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PlaceListResponse(
    val places: List<Place>
)
