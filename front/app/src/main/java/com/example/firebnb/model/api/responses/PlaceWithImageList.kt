package com.example.firebnb.model.api.responses

import com.example.firebnb.model.api.PlaceWithImage
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PlaceWithImageList(
    var places_with_img: List<PlaceWithImage>
)
