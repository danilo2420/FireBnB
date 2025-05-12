package com.example.firebnb.model.api.responses

import com.example.firebnb.model.User
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Users (
    val users: List<User>
)