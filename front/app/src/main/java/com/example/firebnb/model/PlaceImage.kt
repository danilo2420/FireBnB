package com.example.firebnb.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PlaceImage(
    val id: Int,
    @Json(name="place_id") val placeId: Int,
    var img: String?,
    var title: String
)
