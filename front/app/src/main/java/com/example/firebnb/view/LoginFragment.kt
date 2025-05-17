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
import com.example.firebnb.session.Session
import com.example.firebnb.utils.logError
import com.example.firebnb.utils.showToast
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

        // THIS IS FOR TESTING:
        binding.edtLoginEmail.setText("john.doe@example.com")
        binding.edtLoginPassword.setText("pass123")

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        // This logs out the user whenever the login fragment appears (even when popping back into it)
        Session.logOut()
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
            lifecycleScope.launch {
                val email = binding.edtLoginEmail.text.toString()
                val password = binding.edtLoginPassword.text.toString()
                try {
                    if(FirebnbRepository().authUser(email, password)) {
                        val user = checkNotNull(FirebnbRepository().getUserByEmail(email)) {"User is null"}
                        Session.logIn(user)

                        val navController = findNavController()
                        val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
                        navController.navigate(action)
                    } else {
                        showToast("User is does not exist or data is not correct", requireContext())
                    }
                } catch (e: Exception) {
                    showToast("There was an error", requireContext())
                    logError(e)
                }
            }
        }

        binding.btnLoginCreateAccount.setOnClickListener {
            val navController = findNavController()
            val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            navController.navigate(action)
        }
    }

}