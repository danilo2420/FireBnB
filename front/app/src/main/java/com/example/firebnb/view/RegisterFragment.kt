package com.example.firebnb.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.firebnb.databinding.FragmentRegisterBinding
import com.example.firebnb.model.User
import com.example.firebnb.model.api.FirebnbRepository
import com.example.firebnb.utils.logError
import com.example.firebnb.utils.showToast
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RegisterFragment : Fragment() {
    var _binding: FragmentRegisterBinding? = null
    val binding: FragmentRegisterBinding
        get() = checkNotNull(_binding) {"Trying to access null binding"}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initializeBinding(inflater, container)
        initializeEvents()

        return binding.root
    }

    private fun initializeBinding(inflater: LayoutInflater, container: ViewGroup?) {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initializeEvents() {
        binding.btnCreateUser.setOnClickListener {
            lifecycleScope.launch {
                try {
                    val user = createUserFromInput()

                    val success = FirebnbRepository().createUser(user)
                    if (success) {
                        showToast("User was created successfully", requireContext())
                        delay(2000)
                        val navController = findNavController()
                        navController.popBackStack()
                    } else {
                        showToast("User could not be created", requireContext())
                    }
                } catch (e: Exception) {
                    showToast("There was an error", requireContext())
                    logError(e)
                }
            }
        }
    }

    fun createUserFromInput(): User {
        val name = binding.edtName.text.toString()
        val lastName = binding.edtName.text.toString()
        val age = binding.edtAge.text.toString().toInt()
        val nationality = binding.edtNationality.text.toString()
        val email = binding.edtEmail.text.toString()
        val password = binding.edtPassword.text.toString()

        return User(
            null,
            name,
            lastName,
            age,
            nationality,
            null,
            null,
            null,
            email,
            password
        )
    }

}