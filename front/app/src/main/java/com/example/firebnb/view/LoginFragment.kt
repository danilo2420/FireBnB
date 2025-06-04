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
import com.example.firebnb.MainActivity
import com.example.firebnb.databinding.FragmentLoginBinding
import com.example.firebnb.model.api.FirebnbRepository
import com.example.firebnb.session.Session
import com.example.firebnb.utils.logError
import com.example.firebnb.utils.showToast
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class LoginFragment : Fragment() {
    var _binding: FragmentLoginBinding? = null
    val binding: FragmentLoginBinding
        get() = checkNotNull(_binding) {"Trying to access null binding"}

    private var job: Job? = null

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
        setBottomNavVisibility(false)
        turnProgressbarOff()
        // This logs out the user whenever the login fragment appears (even when popping back into it)
        Session.logOut()
    }

    private fun setBottomNavVisibility(visible: Boolean) {
        val mainActivity = activity as MainActivity
        mainActivity.binding.menuBottomNav.visibility = if (visible) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        job?.cancel()
    }

    private fun initializeBinding(inflater: LayoutInflater, container: ViewGroup?) {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
    }

    private fun initializeEvents() {
        binding.btnLoginEnter.setOnClickListener {
            btnLoginEnterClicked()
        }
        binding.btnLoginCreateAccount.setOnClickListener {
            btnLoginCreateAccountClicked()
        }
    }

    private fun btnLoginEnterClicked() {
        turnProgressbarOn()

        val email = binding.edtLoginEmail.text.toString()
        val password = binding.edtLoginPassword.text.toString()

        if (!validateInput(email, password)){
            turnProgressbarOff()
            return
        }

        job = lifecycleScope.launch {
            try {
                if(FirebnbRepository().authUser(email, password)) {
                    val user = checkNotNull(FirebnbRepository().getUserByEmail(email)) {"User is null"}
                    Session.logIn(user)
                    navigateToHome()
                } else {
                    if (isAdded && view != null)
                        showToast("User does not exist or data is not correct", requireContext())
                }
            } catch (e: Exception) {
                if (isAdded && view != null)
                    showToast("There was an error. Please try again.", requireContext())
                logError(e)
            }
            turnProgressbarOff()
        }
    }

    private fun validateInput(email: String, password: String): Boolean {
        if (email.isBlank() || password.isBlank()) {
            if (isAdded && view != null)
                showToast("Fields cannot be empty", requireContext())
            return false
        }

        val regex = Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
        val matches = regex.matches(email)
        if (!matches) {
            if (isAdded && view != null)
                showToast("Email is not valid", requireContext())
            return false
        }

        return true
    }

    private fun navigateToHome() {
        val navController = findNavController()
        val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
        setBottomNavVisibility(true)
        navController.navigate(action)
    }

    private fun btnLoginCreateAccountClicked() {
        val navController = findNavController()
        val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
        navController.navigate(action)
    }

    private fun turnProgressbarOn() {
        if (!isAdded || _binding == null)
            return
        binding.rootLogin.alpha = 0.5f
        binding.progressbarLogin.visibility = View.VISIBLE
    }

    private fun turnProgressbarOff() {
        if (!isAdded || _binding == null)
            return
        binding.rootLogin.alpha = 1f
        binding.progressbarLogin.visibility = View.GONE
    }

}