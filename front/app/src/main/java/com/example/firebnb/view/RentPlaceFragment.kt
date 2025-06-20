package com.example.firebnb.view

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
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
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class RentPlaceFragment : Fragment() {

    var _binding: FragmentRentPlaceBinding? = null
    val binding: FragmentRentPlaceBinding
        get() = checkNotNull(_binding) {"Trying to access null binding"}

    lateinit var place: Place

    var arrivalDate: String? = null
    var endDate: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initializeBinding(inflater, container)
        turnProgressbarOff()
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
                turnProgressbarOn()
                try {
                    val renting = getRenting()
                    if (renting == null)
                        return@launch
                    Log.d("myObject", renting.toString())
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
                turnProgressbarOff()
            }
        }


        binding.btnPickArrivalDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val datePicker = DatePickerDialog(requireContext(),
                { _, year, month, dayOfMonth ->
                    this.arrivalDate = "${year}-${getWithTwoDigits(month+1)}-${getWithTwoDigits(dayOfMonth)}"
                    binding.txtArrivalDate.setText(this.arrivalDate)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePicker.show()
        }

        binding.btnPickEndDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val datePicker = DatePickerDialog(requireContext(),
                { _, year, month, dayOfMonth ->
                    this.endDate = "${year}-${getWithTwoDigits(month+1)}-${getWithTwoDigits(dayOfMonth)}"
                    binding.txtEndDate.setText(this.endDate)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePicker.show()
        }
    }


    private fun getRenting(): Renting? {
        val place_id = place.id
        val guest_id = checkNotNull(Session.getNonNullUser().id)
        val start_date = this.arrivalDate
        val end_date = this.endDate

        if(start_date == null || end_date == null)
            return null

        if (!validateDates(start_date, end_date)){
            turnProgressbarOff()
            return null
        }

        val total_price = place.price_per_night * (ChronoUnit.DAYS.between(
            LocalDate.parse(start_date),
            LocalDate.parse(end_date)
        ))

        return Renting(
            -1,
            place_id,
            guest_id,
            start_date,
            end_date,
            total_price
        )
    }


    private fun validateDates(start_date: String, end_date: String): Boolean {
        val arrival = LocalDate.parse(start_date)
        val departure = LocalDate.parse(end_date)

        if (!arrival.isBefore(departure)) {
            showToast("Arrival date has to come before the end date", requireContext())
            return false
        }

        val today = LocalDate.now()
        if (arrival.isBefore(today) || departure.isBefore(today)) {
            showToast("Dates have to take place after today", requireContext())
            return false
        }

        return true
    }

    private fun getWithTwoDigits(input_: Int): String {
        var input = input_.toString()
        if (input.length == 1) {
            input = "0" + input
        }
        return input
    }

    private fun turnProgressbarOn() {
        binding.frameLayout10.alpha = 0.5f
        binding.progressbarRentPlace.visibility = View.VISIBLE
    }

    private fun turnProgressbarOff() {
        binding.frameLayout10.alpha = 1f
        binding.progressbarRentPlace.visibility = View.GONE
    }

}