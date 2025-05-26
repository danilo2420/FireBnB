package com.example.firebnb.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.firebnb.R
import com.example.firebnb.databinding.FragmentRentalDetailBinding
import com.example.firebnb.model.Renting
import com.example.firebnb.model.api.FirebnbRepository
import com.example.firebnb.utils.logError
import com.example.firebnb.utils.showToast
import kotlinx.coroutines.launch

class RentalDetailFragment : Fragment() {
    var _binding: FragmentRentalDetailBinding? = null
    val binding: FragmentRentalDetailBinding
        get() = checkNotNull(_binding) {"Trying to access null binding"}

    lateinit var renting: Renting

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initializeBinding(inflater, container)
        loadRenting()

        return binding.root
    }

    private fun initializeBinding(inflater: LayoutInflater, container: ViewGroup?) {
        _binding = FragmentRentalDetailBinding.inflate(inflater, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadRenting() {
        val id = RentalDetailFragmentArgs.fromBundle(requireArguments()).rentalId
        lifecycleScope.launch {
            try {
                renting = FirebnbRepository().getRenting(id)
                showToast("Place was loaded successfully", requireContext())
                showData()
            } catch (e: Exception) {
                logError(e)
                showToast("There was an error", requireContext())
            }
        }
    }

    private fun showData() {

    }

}