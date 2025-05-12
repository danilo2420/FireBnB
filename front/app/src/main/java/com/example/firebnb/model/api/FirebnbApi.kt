package com.example.firebnb.model.api

import com.example.firebnb.model.User
import com.example.firebnb.model.api.endpoint_inputs.AuthInput
import com.example.firebnb.model.api.responses.AuthResponse
import com.example.firebnb.model.api.responses.Users
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface FirebnbApi {

    @POST("users/auth")
    suspend fun authUser(
        @Body authInput: AuthInput
    ): AuthResponse

    // Basic endpoints go here
    @GET("users/get")
    suspend fun getAllUsers(): Users

    @GET("users/get")
    suspend fun getUser(
        @Query("id") id:Int
    ): User

}