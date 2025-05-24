package com.example.firebnb.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.firebnb.R
import com.example.firebnb.databinding.FragmentHomeBinding
import com.example.firebnb.databinding.FragmentMyPropertiesBinding
import com.example.firebnb.databinding.FragmentPropertyDetailBinding
import com.example.firebnb.model.Place
import com.example.firebnb.model.api.FirebnbRepository
import com.example.firebnb.utils.logError
import com.example.firebnb.utils.showToast
import kotlinx.coroutines.launch

class PropertyDetailFragment : Fragment() {
    var _binding: FragmentPropertyDetailBinding? = null
    val binding: FragmentPropertyDetailBinding
        get() = checkNotNull(_binding) {"Trying to access a null binding"}

    lateinit var place: Place
    var placeId: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initializeBinding(inflater, container)
        //initializeEvents()
        loadPlaceId()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        loadPlace()
        // showData()
    }

    private fun initializeBinding(inflater: LayoutInflater, container: ViewGroup?) {
        _binding = FragmentPropertyDetailBinding.inflate(inflater, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadPlaceId() {
        this.placeId = PropertyDetailFragmentArgs.fromBundle(requireArguments()).place.id
    }

    private fun loadPlace() {
        lifecycleScope.launch {
            try {
                place = FirebnbRepository().getPlace(placeId)
                showData()
                initializeEvents()
            } catch (e: Exception) {
                showToast("There was an error", requireContext())
                logError(e)
            }
        }
    }

    private fun showData() {
        binding.txtPropertyName.text = "Name: ${this.place.name}"
        binding.txtPropertyOwner.text = "Owner id: " + (if (place.owner_id != null) place.owner_id else "-")
        binding.txtPropertyType.text = "Type: ${this.place.type}"
        binding.txtPropertyDescription.text = "Description: ${this.place.description}"
        binding.txtPropertyPrice.text = "Price: ${this.place.price_per_night}"
        binding.txtPropertyStars.text = "Stars: ${this.place.stars}"
    }

    private fun initializeEvents() {
        binding.btnEditProperty.setOnClickListener {
            val navController = findNavController()
            val action = PropertyDetailFragmentDirections.actionPropertyDetailFragmentToEditPropertyFragment(this.place)
            navController.navigate(action)
        }
    }

}