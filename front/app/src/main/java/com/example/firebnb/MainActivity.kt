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
import com.example.firebnb.utils.logError
import com.example.firebnb.utils.showToast
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeBinding()

        configureBottomNavMenu()

        Log.d("myMessage", "Before method call")

        test()

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

    fun test() {
        lifecycleScope.launch {
            try {
                val list = FirebnbRepository().getAllPlacesWithImage()
                Log.d("myList", list.toString())

            } catch (e: Exception) {
                showToast("Error with new enpoint", this@MainActivity)
                e.printStackTrace()
            }
        }
    }


}

