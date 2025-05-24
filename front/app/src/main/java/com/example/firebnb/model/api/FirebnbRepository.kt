package com.example.firebnb.model.api

import com.example.firebnb.model.Place
import com.example.firebnb.model.User
import com.example.firebnb.model.api.endpoint_inputs.AuthInput
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

class FirebnbRepository {
    val firebnbApi: FirebnbApi
    init {
        firebnbApi = Retrofit.Builder()
            .baseUrl("http://192.168.1.177:5000/") // 10.0.2.2 for emulator
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }

    // USERS
    suspend fun authUser(email: String, password: String): Boolean = firebnbApi.authUser(AuthInput(email, password)).verified

    suspend fun getAllUsers(): List<User> = firebnbApi.getAllUsers().users
    suspend fun getUser(id: Int): User? = firebnbApi.getUser(id = id).users.firstOrNull()
    suspend fun getUserByEmail(email: String): User? = firebnbApi.getUser(email = email).users.firstOrNull()
    suspend fun createUser(user: User): Boolean = firebnbApi.createUser(user).message.contains("success")
    suspend fun updateUser(user: User): Boolean = firebnbApi.updateUser(user).message.contains("success")
    suspend fun deleteUser(id: Int): Boolean = firebnbApi.deleteUser(id).message.contains("success")


    // PLACES
    suspend fun getAllPlaces(): List<Place> = firebnbApi.getPlaces().places
    // This one returns all the places except for the user's properties
    suspend fun getAllPlacesForUser(user: User): List<Place> =
        firebnbApi.getPlaces().places.filter { place -> place.owner_id != user.id }
    // This one only returns the user's properties
    suspend fun getAllPropertiesForUser(user: User): List<Place> =
        firebnbApi.getPlaces().places.filter { place -> place.owner_id == user.id }

}