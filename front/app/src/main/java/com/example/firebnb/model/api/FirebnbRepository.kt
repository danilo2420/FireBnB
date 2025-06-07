package com.example.firebnb.model.api

import com.example.firebnb.model.Place
import com.example.firebnb.model.PlaceImage
import com.example.firebnb.model.Renting
import com.example.firebnb.model.User
import com.example.firebnb.model.api.endpoint_inputs.AuthInput
import com.example.firebnb.model.api.responses.RentingPreview
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

class FirebnbRepository {
    val firebnbApi: FirebnbApi
    init {
        firebnbApi = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:5000/") // local
            // .baseUrl("https://firebnbapi-production.up.railway.app") // deployed api
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
    suspend fun getPlace(id: Int): Place = firebnbApi.getPlace(id).places.first()
    suspend fun getPlaceWithImage(id: Int): PlaceWithImage = firebnbApi.getPlaceWithImage(id)
    suspend fun getAllPlacesWithImage(): List<PlaceWithImage> = firebnbApi.getAllPlacesWithImage().places_with_img
    suspend fun createPlace(place: Place): Boolean = firebnbApi.createPlace(place).message.contains("success")
    suspend fun createPlaceWithImage(placeWithImage: PlaceWithImage): Boolean =
        firebnbApi.createPlaceWithImage(placeWithImage).message.contains("success")
    suspend fun updatePlace(place: Place): Boolean = firebnbApi.updatePlace(place).message.contains("success")
    suspend fun deletePlace(id: Int): Boolean = firebnbApi.deletePlace(id).message.contains("success")

    // PLACE IMAGES
    suspend fun upsertImage(placeImage: PlaceImage): Boolean = firebnbApi.upsertPlaceImage(placeImage).message.contains("success")

    // RENTINGS
    suspend fun getRenting(id: Int): Renting = firebnbApi.getRenting(id).rentings.first()
    suspend fun getRentingPreviewList(id: Int): List<RentingPreview> =
        firebnbApi.getRentingPreviews(id).previews
    suspend fun createRenting(renting: Renting): Boolean = firebnbApi.createRenting(renting).message.contains("success")
    suspend fun deleteRenting(id: Int): Boolean = firebnbApi.deleteRenting(id).message.contains("success")

}