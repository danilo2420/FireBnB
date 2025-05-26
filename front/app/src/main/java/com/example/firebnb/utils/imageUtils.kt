package com.example.firebnb.utils

import android.widget.ImageView
import coil3.load
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@OptIn(ExperimentalEncodingApi::class)
fun ImageView.setImage(image_base64: String?){
    if (image_base64 != null) {
        val image = Base64.decode(image_base64)
        this.load(image)
    } else {
        // default image?
    }
}