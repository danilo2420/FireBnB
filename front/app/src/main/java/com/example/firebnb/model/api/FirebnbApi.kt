package com.example.firebnb.model.api

import com.example.firebnb.model.Place
import com.example.firebnb.model.User
import com.example.firebnb.model.api.endpoint_inputs.AuthInput
import com.example.firebnb.model.api.responses.AuthResponse
import com.example.firebnb.model.api.responses.PlaceListResponse
import com.example.firebnb.model.api.responses.RentingPreviewList
import com.example.firebnb.model.api.responses.SuccessResponse
import com.example.firebnb.model.api.responses.Users
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface FirebnbApi {


    // USER ******************************
    @POST("users/auth")
    suspend fun authUser(
        @Body authInput: AuthInput
    ): AuthResponse

    @GET("users/get")
    suspend fun getAllUsers(): Users

    @GET("users/get")
    suspend fun getUser(
        @Query("id") id: Int? = null,
        @Query("email") email: String? = null
    ): Users

    @POST("users/create")
    suspend fun createUser(
        @Body user: User
    ): SuccessResponse

    @PUT("users/update")
    suspend fun updateUser(
        @Body user: User
    ): SuccessResponse

    @DELETE("users/delete")
    suspend fun deleteUser(
        @Query("id") id: Int
    ): SuccessResponse


    // PLACE ******************************
    @GET("places/get")
    suspend fun getPlaces(): PlaceListResponse

    @GET("places/get")
    suspend fun getPlace(
        @Query("id") id: Int
    ): PlaceListResponse

    @POST("places/create")
    suspend fun createPlace(
        @Body place: Place
    ): SuccessResponse

    @PUT("places/update")
    suspend fun updatePlace(
        @Body place: Place
    ): SuccessResponse

    @DELETE("places/delete")
    suspend fun deletePlace(
        @Query("id") id: Int
    ): SuccessResponse


    // RENTING ******************************
    @GET("rentings/get_previews")
    suspend fun getRentingPreviews(
        @Query("id") id: Int
    ): RentingPreviewList

    @POST("rentings/create")
    suspend fun createRenting(
        @Body renting: Renting
    ): SuccessResponse
}