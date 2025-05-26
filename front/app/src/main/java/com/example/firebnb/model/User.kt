package com.example.firebnb.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
    val id: Int?,
    val name: String?,
    val lastName: String?,
    val age: Int?,
    val nationality: String?,
    val description: String?,
    var profile_image: String?,
    val stars: Int?,
    val email: String?,
    val password: String?
)