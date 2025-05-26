package com.example.firebnb.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.firebnb.R
import com.example.firebnb.databinding.FragmentProfileBinding
import com.example.firebnb.model.User
import com.example.firebnb.session.Session
import com.example.firebnb.utils.logMessage
import com.example.firebnb.utils.setImage


class ProfileFragment : Fragment() {
    var _binding: FragmentProfileBinding? = null
    val binding: FragmentProfileBinding
        get() = checkNotNull(_binding) {"Trying to access wrong binding"}

    lateinit var user: User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initializeBinding(inflater, container)
        initializeEvents()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        user = Session.getNonNullUser()
        showUserData()
    }

    private fun initializeBinding(inflater: LayoutInflater, container: ViewGroup?) {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showUserData() {
        binding.apply {
            txtName2.text = "Name: ${user.name} ${user.lastName}"
            txtAge2.text = "Age: ${user.age}"
            txtDescription2.text = "Description: ${user.description}"
            txtEmail2.text = "Email: ${user.email}"
            txtNationality2.text = "Nationality: ${user.nationality}"
            txtStars2.text = "Stars: ${user.stars.toString()}"
            imgProfile.setImage(user.profile_image)
        }
    }

    private fun initializeEvents() {
        binding.btnGoToEditProfile.setOnClickListener {
            val navController = findNavController()
            val action = ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment()
            navController.navigate(action)
        }

    }

}