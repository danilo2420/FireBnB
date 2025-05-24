package com.example.firebnb.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.firebnb.R
import com.example.firebnb.databinding.FragmentEditPropertyBinding
import com.example.firebnb.model.Place
import com.example.firebnb.model.api.FirebnbRepository
import com.example.firebnb.utils.logError
import com.example.firebnb.utils.logMessage
import com.example.firebnb.utils.showToast
import kotlinx.coroutines.launch

class EditPropertyFragment : Fragment() {
    var _binding: FragmentEditPropertyBinding? = null
    val binding: FragmentEditPropertyBinding
        get() = checkNotNull(_binding) {"Trying to access null binding"}

    lateinit var place: Place

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initializeBinding(inflater, container)
        loadData()
        showData()
        initializeEvents()

        return binding.root
    }

    private fun initializeBinding(inflater: LayoutInflater, container: ViewGroup?) {
        _binding = FragmentEditPropertyBinding.inflate(inflater, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadData() {
        this.place = EditPropertyFragmentArgs.fromBundle(requireArguments()).place
    }

    private fun showData() {
        binding.apply {
            edtPropertyName.setText(place.name)
            edtPropertyType.setText(place.type)
            edtPropertyDescription.setText(place.description)
            edtPropertyPrice.setText(place.price_per_night.toString())
        }
    }


    private fun initializeEvents() {
        initializeBtnSaveProfile()

    }

    private fun initializeBtnSaveProfile() {
        binding.btnSaveProfile.setOnClickListener {
            lifecycleScope.launch {
                try {
                    // get data
                    val _place = getPlaceFromInput()

                    // call the api
                    val success = FirebnbRepository().updatePlace(_place)

                    // check the result
                    if (success) {
                        showToast("Place was updated successfully", requireContext())
                        findNavController().popBackStack()
                    } else {
                        showToast("API call was not successful", requireContext())
                    }
                } catch (e: Exception) {
                    showToast("There was an error", requireContext())
                    logError(e)
                }

            }
        }
    }

    private fun getPlaceFromInput(): Place {
        val id = place.id
        val owner_id = place.owner_id
        val name = binding.edtPropertyName.text.toString()
        val type = binding.edtPropertyType.text.toString()
        val description = binding.edtPropertyDescription.text.toString()
        var price_per_night = binding.edtPropertyPrice.text.toString().toFloat()
            //.toFloatOrNull()
        /*
        if (price_per_night == null) {
            price_per_night = place.price_per_night
        }*/

        return Place(
            id,
            owner_id,
            name,
            type,
            description,
            price_per_night,
            0
        )
    }

}