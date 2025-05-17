package com.example.firebnb.utils

import android.content.Context
import android.util.Log
import android.widget.Toast

fun showToast(message: String, context: Context) {
    Toast.makeText(
        context,
        message,
        Toast.LENGTH_LONG
    ).show()
}

fun logError(e: Exception) {
    Log.d("myError", "Error: ${e}")
}