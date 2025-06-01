package com.example.firebnb.model.api

import com.example.firebnb.model.Place
import com.example.firebnb.model.PlaceImage
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class PlaceWithImage(
    val place: Place,
    val image: PlaceImage?
): Serializable