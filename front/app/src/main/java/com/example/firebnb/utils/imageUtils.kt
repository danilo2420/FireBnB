package com.example.firebnb.utils

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import coil3.load
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@OptIn(ExperimentalEncodingApi::class)
fun ImageView.setImage(image_base64: String?){
    if (image_base64 != null) {
        try {
            // val image = Base64.decode(image_base64)
            val image = android.util.Base64.decode(image_base64, android.util.Base64.DEFAULT)
            this.load(image)
        } catch (e: Exception) {
            Log.d("abcdef", "error decoding image")
            logError(e)
        }
    }
}

fun getBase64FromFileUri(uri: Uri, context: Context): String? {
    try {
        val inputStream = context.contentResolver.openInputStream(uri)
        val bytes = inputStream?.readBytes()
        val base64String = android.util.Base64.encodeToString(bytes, android.util.Base64.DEFAULT)
        return base64String
    } catch (e: Exception) {
        logError(e)
        return null
    }
}