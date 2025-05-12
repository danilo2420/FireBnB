package com.example.firebnb.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.firebnb.databinding.FragmentLoginBinding
import com.example.firebnb.model.api.FirebnbRepository
import kotlinx.coroutines.launch


class LoginFragment : Fragment() {
    var _binding: FragmentLoginBinding? = null
    val binding: FragmentLoginBinding
        get() = checkNotNull(_binding) {"Trying to access null binding"}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initializeBinding(inflater, container)
        initializeEvents()

        Log.d("myMessage", "Before method call")
        testApi()


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initializeBinding(inflater: LayoutInflater, container: ViewGroup?) {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
    }

    private fun initializeEvents() {
        binding.btnGoToRegister.setOnClickListener {
            val navController = findNavController()
            val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            navController.navigate(action)
        }
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

}