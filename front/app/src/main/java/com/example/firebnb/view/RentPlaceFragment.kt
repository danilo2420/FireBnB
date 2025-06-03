package com.example.firebnb.view

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.firebnb.R
import com.example.firebnb.databinding.FragmentRentPlaceBinding
import com.example.firebnb.model.Place
import com.example.firebnb.model.api.FirebnbRepository
import com.example.firebnb.model.Renting
import com.example.firebnb.session.Session
import com.example.firebnb.utils.logError
import com.example.firebnb.utils.showToast
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class RentPlaceFragment : Fragment() {

    var _binding: FragmentRentPlaceBinding? = null
    val binding: FragmentRentPlaceBinding
        get() = checkNotNull(_binding) {"Trying to access null binding"}

    lateinit var place: Place

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initializeBinding(inflater, container)
        loadPlace()
        initializeEvents()

        return binding.root
    }

    private fun initializeBinding(inflater: LayoutInflater, container: ViewGroup?) {
        _binding = FragmentRentPlaceBinding.inflate(inflater, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadPlace() {
        this.place = RentPlaceFragmentArgs.fromBundle(requireArguments()).place
        binding.txtPlaceToRentName.setText(this.place.name)
    }

    private fun initializeEvents() {
        binding.btnRentPlace.setOnClickListener {
            lifecycleScope.launch {
                try {
                    val renting = getRenting()
                    val success = FirebnbRepository().createRenting(renting)

                    if (success) {
                        showToast("Place was rented successfully!", requireContext())
                        findNavController().popBackStack()
                    } else {
                        showToast("There was an error", requireContext())
                    }
                } catch (e: Exception) {
                    showToast("There was an error", requireContext())
                    logError(e)
                }
            }
        }

        binding.btnDatePicker.setOnClickListener {
            val calendar = Calendar.getInstance()
            val datePicker = DatePickerDialog(requireContext(),
                { _, year, month, dayOfMonth ->
                    showToast("${dayOfMonth}-${month}-${year}", requireContext())
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePicker.show()
        }
    }

    private fun getRenting(): Renting {
        val place_id = place.id
        val guest_id = checkNotNull(Session.getNonNullUser().id)
        val start_date = binding.edtStartDate.text.toString()
        val end_date = binding.edtEndDate.text.toString()
        val total_price = place.price_per_night // TODO: this would have to be calculated based on the dates

        return Renting(
            -1,
            place_id,
            guest_id,
            start_date,
            end_date,
            total_price
        )
    }

}