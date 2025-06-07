package com.example.firebnb.session

import android.util.Log
import com.example.firebnb.model.User
import com.example.firebnb.model.api.FirebnbRepository
import com.example.firebnb.utils.logError
import com.example.firebnb.utils.logMessage
import com.example.firebnb.utils.showToast

object Session {
    var user: User? = null

    fun isLoggedIn(): Boolean = user != null

    fun logIn(user: User) {
        this.user = user
    }

    fun logOut() {
        logMessage("Log out method was run")
        this.user = null
    }

    fun getNonNullUser() =
        checkNotNull(user){"User is null in session object and it tried to be accessed"}

    suspend fun updateUser(): Boolean {
        try {
            this.user = FirebnbRepository().getUser(checkNotNull(getNonNullUser().id))
            logMessage("User was updated successfully")
            return true
        } catch (e: Exception) {
            logError(e)
            return false
        }
    }
}