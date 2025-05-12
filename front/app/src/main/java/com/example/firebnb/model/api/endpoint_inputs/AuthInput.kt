package com.example.firebnb.model.api.endpoint_inputs

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AuthInput(
    val email: String,
    val password: String
)
