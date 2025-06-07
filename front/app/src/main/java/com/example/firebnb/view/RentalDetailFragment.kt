package com.example.firebnb.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.firebnb.R
import com.example.firebnb.databinding.FragmentRentalDetailBinding
import com.example.firebnb.model.Place
import com.example.firebnb.model.Renting
import com.example.firebnb.model.api.FirebnbRepository
import com.example.firebnb.model.api.PlaceWithImage
import com.example.firebnb.utils.logError
import com.example.firebnb.utils.setImage
import com.example.firebnb.utils.showToast
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RentalDetailFragment : Fragment() {
    var _binding: FragmentRentalDetailBinding? = null
    val binding: FragmentRentalDetailBinding
        get() = checkNotNull(_binding) {"Trying to access null binding"}

    lateinit var renting: Renting
    lateinit var placeWithImage: PlaceWithImage

    private var job1: Job? = null
    private var job2: Job? = null

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
        job1?.cancel()
        job2?.cancel()
    }

    private fun loadRenting() {
        val id = RentalDetailFragmentArgs.fromBundle(requireArguments()).rentalId
        job1 = lifecycleScope.launch {
            turnProgressbarOn()
            try {
                renting = FirebnbRepository().getRenting(id)
                placeWithImage = FirebnbRepository().getPlaceWithImage(renting.place_id)
                showData()
                initializeEvents()
            } catch (e: Exception) {
                logError(e)
                //showToast("There was an error", requireContext())
            }
            turnProgressbarOff()
        }
    }

    private fun showData() {
        if (!isAdded || _binding == null)
            return
        binding.apply {
            txtRentalDetailStartDate.setText("Arrival date: ${renting.start_date}")
            txtRentalDetailEndDate.setText("End date: ${renting.end_date}")
            txtRentalDetailPrice.setText("Price: ${renting.total_price}")
            txtRentalDetailPlaceName.setText(this@RentalDetailFragment.placeWithImage.place.name)
            imageView4.setImage(this@RentalDetailFragment.placeWithImage.image?.img)
        }
    }

    private fun initializeEvents() {
        binding.btnDeleteRental.setOnClickListener {
            job2 = lifecycleScope.launch {
                try {
                    val success = FirebnbRepository().deleteRenting(renting.id)
                    if (success) {
                        showToast("Rental deleted successfully", requireContext())
                        findNavController().popBackStack()
                    } else {
                        //showToast("There was an error", requireContext())
                    }
                } catch (e: Exception) {
                    //showToast("There was an error", requireContext())
                    logError(e)
                }
            }
        }
    }

    private fun turnProgressbarOn() {
        if (!isAdded || _binding == null)
            return
        binding.rootRentalDetail.visibility = View.GONE
        binding.progressbarRentalDetail.visibility = View.VISIBLE
    }

    private fun turnProgressbarOff() {
        if (!isAdded || _binding == null)
            return
        binding.rootRentalDetail.visibility = View.VISIBLE
        binding.progressbarRentalDetail.visibility = View.GONE
    }

}