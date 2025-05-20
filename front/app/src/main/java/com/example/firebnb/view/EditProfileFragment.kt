package com.example.firebnb.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.firebnb.R
import com.example.firebnb.databinding.FragmentEditProfileBinding
import com.example.firebnb.model.User
import com.example.firebnb.session.Session

class EditProfileFragment : Fragment() {
    var _binding: FragmentEditProfileBinding? = null
    val binding: FragmentEditProfileBinding
        get() = checkNotNull(_binding) {"Trying to access wrong binding"}

    lateinit var user: User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initializeBinding(inflater, container)
        user = Session.getNonNullUser()
        showUserData()
        initializeEvents()

        return binding.root
    }

    private fun initializeBinding(inflater: LayoutInflater, container: ViewGroup?) {
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showUserData() {
        binding.apply {
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
        binding.btnSave.setOnClickListener {
            val _user = getUserFromInput()
        }
    }

    private fun getUserFromInput(): User {
        val name = binding.edtName2.text.toString()
        val lastName = binding.edtLastName2.text.toString()
        val age = binding.edtAge2.text.toString().toInt()
        val description = binding.edtDescription2.text.toString()
        val email = binding.edtEmail2.text.toString()
        val nationality = binding.edtNationality2.text.toString()

        return User(
            -1, name, lastName, age, nationality, description, user.profile_image, user.stars, email, user.password
        )
    }

}