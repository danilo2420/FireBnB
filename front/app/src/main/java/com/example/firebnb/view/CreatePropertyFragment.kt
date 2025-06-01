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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.firebnb.R
import com.example.firebnb.databinding.FragmentCreatePropertyBinding
import com.example.firebnb.model.Place
import com.example.firebnb.model.PlaceImage
import com.example.firebnb.model.api.FirebnbRepository
import com.example.firebnb.session.Session
import com.example.firebnb.utils.getBase64FromFileUri
import com.example.firebnb.utils.logError
import com.example.firebnb.utils.showToast
import kotlinx.coroutines.launch

class CreatePropertyFragment : Fragment() {

    var _binding: FragmentCreatePropertyBinding? = null
    val binding: FragmentCreatePropertyBinding
        get() = checkNotNull(_binding) {"Trying to access null binding"}

    private var image: PlaceImage? = null

    private lateinit var openFileLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        openFileLauncher = registerForActivityResult(
            ActivityResultContracts.OpenDocument()) { uri -> handleUri(uri) }
    }

    private fun openFileChooser() {
        openFileLauncher.launch(arrayOf("image/*"))
    }

    fun handleUri(uri: Uri?) {
        if (uri == null) return

        try {
            val image = getBase64FromFileUri(uri, requireContext())
            if (image != null) {
                // TODO: implement this
                // this.image = PlaceImage(-1)
                //updateProfileImage(image)

            } else {
                Log.d("myMessage", "Error with the image thingy")
            }
        } catch (e: Exception) {
            logError(e)
            showToast("Error in image conversion", requireContext())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initializeBinding(inflater, container)
        initializeEvents()

        return binding.root
    }

    private fun initializeBinding(inflater: LayoutInflater, container: ViewGroup?) {
        _binding = FragmentCreatePropertyBinding.inflate(inflater, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun initializeEvents() {
        binding.btnCreateProperty.setOnClickListener {
            lifecycleScope.launch {
                try {
                    // get data
                    val place = getPlaceFromInput()

                    // send it to api
                    val success = FirebnbRepository().createPlace(place)
                    if (success) {
                        showToast("Place was created successfully", requireContext())
                        findNavController().popBackStack()
                    } else {
                        showToast("There was a problem", requireContext())
                    }

                    // navigate back if successful
                    // is recycler view updating successfully?
                } catch (e: Exception) {
                    logError(e)
                }

            }
        }
    }

    private fun getPlaceFromInput(): Place {
        val id = -1 // sqlalchemy should ignore this
        val name = binding.edtCreatePropertyName.text.toString()
        val type = binding.edtCreatePropertyType.text.toString()
        val description = binding.edtCreatePropertyDescription.text.toString()
        val price = binding.edtCreatePropertyPrice.text.toString().toFloat()

        return Place(
            id,
            Session.getNonNullUser().id,
            name,
            type,
            description,
            price,
            0
        )
    }
}