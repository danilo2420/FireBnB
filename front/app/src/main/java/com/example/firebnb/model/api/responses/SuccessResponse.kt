package com.example.firebnb.model.api.responses

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SuccessResponse(
    val message: String
)
