package com.example.firebnb.session

import com.example.firebnb.model.User
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
}