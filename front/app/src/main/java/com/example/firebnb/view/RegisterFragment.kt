package com.example.firebnb.view

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.firebnb.databinding.FragmentRegisterBinding
import com.example.firebnb.model.User
import com.example.firebnb.model.api.FirebnbRepository
import com.example.firebnb.utils.getBase64FromFileUri
import com.example.firebnb.utils.logError
import com.example.firebnb.utils.showToast
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RegisterFragment : Fragment() {
    var _binding: FragmentRegisterBinding? = null
    val binding: FragmentRegisterBinding
        get() = checkNotNull(_binding) {"Trying to access null binding"}

    private lateinit var openFileLauncher: ActivityResultLauncher<Array<String>>
    private var chosenImage: String? = null

    private var job: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        openFileLauncher = registerForActivityResult(
            ActivityResultContracts.OpenDocument()) { uri -> handleUri(uri) }
    }

    fun handleUri(uri: Uri?) {
        if (uri == null) return

        try {
            val image = getBase64FromFileUri(uri, requireContext())
            if (image != null) {
                binding.imgRegisterUser.setImageURI(uri)
                chosenImage = image
                // updateProfileImage(image)
            } else {
                Log.d("myMessage", "Error with the image thingy")
            }
        } catch (e: Exception) {
            logError(e)
            showToast("Error in image conversion", requireContext())
        }
    }

    private fun openFileChooser() {
        openFileLauncher.launch(arrayOf("image/*"))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initializeBinding(inflater, container)
        initializeEvents()
        turnProgressbarOff()

        return binding.root
    }

    private fun initializeBinding(inflater: LayoutInflater, container: ViewGroup?) {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        job?.cancel()
    }

    private fun initializeEvents() {
        binding.btnCreateUser.setOnClickListener {
            btnCreateUserClicked()
        }

        binding.btnRegisterChooseImage.setOnClickListener {
            openFileChooser()
        }
    }

    private fun btnCreateUserClicked() {
        val user = createUserFromInput() ?: return

        job = lifecycleScope.launch {
            turnProgressbarOn()
            try {
                val success = FirebnbRepository().createUser(user)
                if (success) {
                    showToast("User was created successfully", requireContext())
                    val navController = findNavController()
                    navController.popBackStack()
                } else {
                    showToast("User could not be created", requireContext())
                }
            } catch (e: Exception) {
                showToast("There was an error", requireContext())
                logError(e)
            }
            turnProgressbarOff()
        }
    }

    fun createUserFromInput(): User? {
        val name = binding.edtName.text.toString()
        val lastName = binding.edtLastname.text.toString()
        val age = binding.edtAge.text.toString()
        val nationality = binding.edtNationality.text.toString()
        val email = binding.edtEmail.text.toString()
        val password = binding.edtPassword.text.toString()
        val password2 = binding.edtPassword2.text.toString()

        if (!validateInput(
            name,
            lastName,
            age,
            nationality,
            email,
            password,
            password2
        )) {
            return null
        }

        val ageInt = age.toInt()

        return User(
            null,
            name,
            lastName,
            ageInt,
            nationality,
            null,
            chosenImage,
            null,
            email,
            password
        )
    }

    private fun validateInput(
        name: String,
        lastName: String,
        age: String,
        nationality: String,
        email: String,
        password: String,
        password2: String
    ): Boolean {

        if (
            name.isBlank() ||
            lastName.isBlank() ||
            age.isBlank() ||
            nationality.isBlank() ||
            email.isBlank() ||
            password.isBlank() ||
            password2.isBlank()
        ) {
            showToast("Fields cannot be blank", requireContext())
            return false
        }

        if (password != password2) {
            showToast("Passwords must be the same", requireContext())
            return false
        }

        val regex = Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
        val matches = regex.matches(email)
        if (!matches) {
            showToast("Email is not valid", requireContext())
            return false
        }

        return true
    }

    private fun turnProgressbarOn() {
        if (!isAdded || _binding == null)
            return
        binding.rootRegister.alpha = 0.5f
        binding.progressbarRegister.visibility = View.VISIBLE
    }

    private fun turnProgressbarOff() {
        if (!isAdded || _binding == null)
            return
        binding.rootRegister.alpha = 1f
        binding.progressbarRegister.visibility = View.GONE
    }

}