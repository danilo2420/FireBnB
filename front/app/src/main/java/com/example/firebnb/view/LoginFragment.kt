package com.example.firebnb.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

        binding.edtLoginEmail.setText("john.doe@example.com")
        binding.edtLoginPassword.setText("pass123")

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
        binding.btnLoginEnter.setOnClickListener {
            lifecycleScope.launch { // TODO: I'll have to fix that in order to verify user into the app
                val email = binding.edtLoginEmail.text.toString()
                val password = binding.edtLoginPassword.text.toString()

                Log.d("myMessage", "email: ${email}; password: ${password}")

                val verified = FirebnbRepository().authUser(email, password)

                if(verified) {
                    // val navController = findNavController()
                    showToast("User was verified")
                } else {
                    showToast("User was NOT verifiedq")
                }
            }
        }

        binding.btnLoginCreateAccount.setOnClickListener {
            val navController = findNavController()
            val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            navController.navigate(action)
        }
    }

    fun showToast(message: String) {
        Toast.makeText(
            requireContext(),
            message,
            Toast.LENGTH_LONG
        ).show()
    }

}