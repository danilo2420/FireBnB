package com.example.firebnb

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.firebnb.model.api.FirebnbRepository
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("myMessage", "Before method call")
        testApi()
        testAuth()

        setContentView(R.layout.activity_main)
    }

    fun testApi() {
        lifecycleScope.launch {
            try {
                Log.d("myMessage", "Hello")
                FirebnbRepository()
                    .getAllUsers()
                    .forEach { user -> Log.d("myMessage", user.toString()) }
            } catch (e:Exception) {
                Log.d("myMessage", e.toString())
            }
        }
    }

    fun testAuth() {
        lifecycleScope.launch {
            try {
                Log.d("myMessage", "We got in auth method")
                val email = "john.doe@example.com"
                val password = "pass123"
                val verified = FirebnbRepository()
                    .authUser(email, password)
                Log.d("myMessage", "Was the user verified? ${verified}")
            } catch (e:Exception) {
                Log.d("myMessage", e.toString())
            }
        }
    }


}

