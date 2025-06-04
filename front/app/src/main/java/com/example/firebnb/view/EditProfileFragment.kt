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
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.firebnb.R
import com.example.firebnb.databinding.FragmentEditProfileBinding
import com.example.firebnb.model.User
import com.example.firebnb.model.api.FirebnbRepository
import com.example.firebnb.session.Session
import com.example.firebnb.utils.getBase64FromFileUri
import com.example.firebnb.utils.logError
import com.example.firebnb.utils.setImage
import com.example.firebnb.utils.showToast
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class EditProfileFragment : Fragment() {
    var _binding: FragmentEditProfileBinding? = null
    val binding: FragmentEditProfileBinding
        get() = checkNotNull(_binding) {"Trying to access wrong binding"}

    lateinit var user: User

    private lateinit var openFileLauncher: ActivityResultLauncher<Array<String>>

    private var job1: Job? = null
    private var job2: Job? = null
    private var job3: Job? = null

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
                updateProfileImage(image)
            } else {
                Log.d("myMessage", "Error with the image thingy")
            }
        } catch (e: Exception) {
            logError(e)
            showToast("Error in image conversion", requireContext())
        }
    }

    private fun updateProfileImage(image: String) {
        job1 = lifecycleScope.launch {
            try {
                user.profile_image = image
                // Log.d("abcdefg", user.profile_image)
                val success = FirebnbRepository().updateUser(user)
                if (success) {
                    showToast("Profile image updated successfully", requireContext())
                    showUserData()
                }
            } catch (e: Exception) {
                showToast("There was an error updating the profile picture", requireContext())
                logError(e)
            }
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
        user = Session.getNonNullUser()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        showUserData()
        initializeEvents()
    }

    private fun initializeBinding(inflater: LayoutInflater, container: ViewGroup?) {
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        job1?.cancel()
        job2?.cancel()
        job3?.cancel()
    }

    private fun showUserData() {
        if (!isAdded || _binding == null)
            return
        binding.apply {
            if (user.profile_image != null)
                imgEditProfile.setImage(user.profile_image)
            edtName2.setText(user.name)
            edtLastName2.setText(user.lastName)
            edtAge2.setText(user.age.toString())
            edtDescription2.setText(user.description)
            edtEmail2.setText(user.email)
            edtNationality2.setText(user.nationality)
            // Add image
        }
    }

    private fun initializeEvents() {
        initializeBtnSave()
        initializeBtnDeleteProfile()
        initializeBtnChooseImage()
    }

    private fun initializeBtnSave() {
        binding.btnSave.setOnClickListener {
            val _user = getUserFromInput()
            if (_user == null)
                return@setOnClickListener

            job2 = lifecycleScope.launch {
                try {
                    val success = FirebnbRepository().updateUser(_user)

                    if(success) {
                        showToast("Profile updated successfully", requireContext())
                        val userUpdated = Session.updateUser()
                        if(userUpdated) {
                            findNavController().popBackStack()
                        } else {
                            showToast("There was some type of error updating the global user inside the app", requireContext())
                        }
                    } else {
                        showToast("There was an error", requireContext())
                    }
                } catch (e: Exception) {
                    logError(e)
                }
            }
        }
    }

    private fun getUserFromInput(): User? {
        if (!isAdded || _binding == null)
            return null
        val name = binding.edtName2.text.toString()
        val lastName = binding.edtLastName2.text.toString()
        val age = binding.edtAge2.text.toString()
        val description = binding.edtDescription2.text.toString()
        val email = binding.edtEmail2.text.toString()
        val nationality = binding.edtNationality2.text.toString()

        if (!validateInput(
            name, lastName, age, description, email, nationality
        )) return null

        val ageInt = age.toInt()

        return User(
            Session.getNonNullUser().id,
            name,
            lastName,
            ageInt,
            nationality,
            description,
            user.profile_image,
            user.stars,
            email,
            user.password
        )
    }

    private fun validateInput(name: String, lastName: String, age: String, description: String, email: String, nationality: String): Boolean {
        if (name.isBlank() ||
            lastName.isBlank() ||
            age.isBlank() ||
            description.isBlank() ||
            email.isBlank() ||
            nationality.isBlank()) {
            showToast("Fields cannot be blank", requireContext())
            return false
        }

        try {
            age.toInt()
        } catch (e: Exception) {
            showToast("Age has to be a number", requireContext())
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

    private fun initializeBtnDeleteProfile() {
        binding.btnDeleteProfile.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Delete profile")
                .setMessage("All your data will be permanently deleted")
                .setPositiveButton("Yes") {_, _, ->
                    job3 = lifecycleScope.launch {
                        try {
                            val success = FirebnbRepository().deleteUser(checkNotNull(user.id))
                            if(success) {
                                showToast("User deleted successfully", requireContext())
                                val navController = findNavController()
                                val action = EditProfileFragmentDirections.actionEditProfileFragmentToLoginFragment()
                                navController.navigate(action)
                            }
                        } catch (e: Exception) {
                            showToast("There was an error", requireContext())
                            logError(e)
                        }
                    }
                }
                .setNegativeButton("No", null)
                .show()
        }
    }

    private fun initializeBtnChooseImage() {
        binding.btnChooseImage.setOnClickListener {
            openFileChooser()
        }
    }

}