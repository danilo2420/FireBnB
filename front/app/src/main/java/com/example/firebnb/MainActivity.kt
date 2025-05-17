package com.example.firebnb

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.firebnb.databinding.ActivityMainBinding
import com.example.firebnb.model.api.FirebnbRepository
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeBinding()

        configureBottomNavMenu()

        Log.d("myMessage", "Before method call")
        testApi()
        testAuth()

        setContentView(binding.root)
    }

    private fun initializeBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
    }

    private fun configureBottomNavMenu() {
        val navController = getNavController()
        NavigationUI.setupWithNavController(binding.menuBottomNav, navController)
    }

    private fun getNavController(): NavController {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragment_container_view)
            as NavHostFragment
        return navHostFragment.navController
    }

    // API TESTS
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

