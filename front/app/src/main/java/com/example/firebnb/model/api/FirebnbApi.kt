package com.example.firebnb.model.api

import com.example.firebnb.model.User
import com.example.firebnb.model.api.responses.Users
import retrofit2.http.GET
import retrofit2.http.Query

interface FirebnbApi {
    // Basic endpoints go here
    @GET("users/get")
    suspend fun getAllUsers(): Users

    @GET("users/get")
    suspend fun getUser(
        @Query("id") id:Int
    ): User

}