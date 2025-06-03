package com.example.firebnb.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
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
        turnProgressbarOn()
        user = Session.getNonNullUser()
        showUserData()
        turnProgressbarOff()
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
            txtName2.text = "${user.name} ${user.lastName}"
            txtAge2.text = user.age.toString()
            txtDescription2.text = user.description
            txtEmail2.text = user.email
            txtNationality2.text = user.nationality
            //txtStars2.text = "Stars: ${user.stars.toString()}"
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

    private fun turnProgressbarOn() {
        binding.rootProfile.visibility = View.GONE
        binding.progressbarProfile.visibility = View.VISIBLE
    }

    private fun turnProgressbarOff() {
        binding.rootProfile.visibility = View.VISIBLE
        binding.progressbarProfile.visibility = View.GONE
    }

}