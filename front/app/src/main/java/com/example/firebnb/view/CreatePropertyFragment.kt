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
import com.example.firebnb.model.api.PlaceWithImage
import com.example.firebnb.session.Session
import com.example.firebnb.utils.getBase64FromFileUri
import com.example.firebnb.utils.logError
import com.example.firebnb.utils.setImage
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
                this.image = PlaceImage(-1, -1, image, "")
                binding.imageView6.setImage(image)
            } else {
                Log.d("myMessage", "There was an error. Please try again")
            }
        } catch (e: Exception) {
            logError(e)
            showToast("There was an error. Please try again", requireContext())
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
            Log.d("myCounter", "1")
            val place = getPlaceFromInput()
            if (place == null)
                return@setOnClickListener

            Log.d("myCounter", "2")

            lifecycleScope.launch {
                try {
                    Log.d("myCounter", "3")
                    val placeWithImage = PlaceWithImage(
                        place,
                        this@CreatePropertyFragment.image
                    )

                    val success = FirebnbRepository().createPlaceWithImage(placeWithImage)
                    if (success) {
                        showToast("Place was created successfully", requireContext())
                        findNavController().popBackStack()
                    } else {
                        showToast("There was a problem", requireContext())
                    }
                } catch (e: Exception) {
                    logError(e)
                }

            }
        }
        binding.btnCreatePropertyImage.setOnClickListener {
            openFileChooser()
        }
    }

    private fun getPlaceFromInput(): Place? {
        val id = -1 // sqlalchemy should ignore this
        val name = binding.edtCreatePropertyName.text.toString()
        val type = binding.edtCreatePropertyType.text.toString()
        val description = binding.edtCreatePropertyDescription.text.toString()
        val price = binding.edtCreatePropertyPrice.text.toString()

        if (!validateInput(name, type, description, price)) {
            return null
        }

        val price_ = price.toFloat()

        return Place(
            id,
            Session.getNonNullUser().id,
            name,
            type,
            description,
            price_,
            0
        )
    }

    private fun validateInput(name: String, type: String, description: String, price: String): Boolean {
        if (name.isBlank() ||
            type.isBlank() ||
            description.isBlank() ||
            price.isBlank()) {
            showToast("Fields cannot be blank", requireContext())
            return false
        }

        try {
            price.toFloat()
        } catch (e: Exception) {
            showToast("Price has to be a number", requireContext())
            return false
        }

        return true
    }
}