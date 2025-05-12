package com.example.firebnb.model.api

import com.example.firebnb.model.User
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

class FirebnbRepository {
    val firebnbApi: FirebnbApi
    init {
        firebnbApi = Retrofit.Builder()
            .baseUrl("http://192.168.1.174:5000/") // 10.0.2.2 for emulator
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }

    // Endpoint methods
    suspend fun getAllUsers(): List<User> = firebnbApi.getAllUsers().users
    suspend fun getUser(id:Int): User = firebnbApi.getUser(id)

}