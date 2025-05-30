package com.example.firebnb.model.api.responses

import com.example.firebnb.model.Place
import com.example.firebnb.model.PlaceImage
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PlaceWithImage(
    val place: Place,
    val image: PlaceImage?
)