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
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.firebnb.databinding.FragmentEditPropertyBinding
import com.example.firebnb.model.Place
import com.example.firebnb.model.PlaceImage
import com.example.firebnb.model.api.FirebnbRepository
import com.example.firebnb.utils.getBase64FromFileUri
import com.example.firebnb.utils.logError
import com.example.firebnb.utils.setImage
import com.example.firebnb.utils.showToast
import kotlinx.coroutines.launch

class EditPropertyFragment : Fragment() {
    var _binding: FragmentEditPropertyBinding? = null
    val binding: FragmentEditPropertyBinding
        get() = checkNotNull(_binding) {"Trying to access null binding"}

    private lateinit var openFileLauncher: ActivityResultLauncher<Array<String>>

    lateinit var place: Place
    var image: PlaceImage? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        openFileLauncher = registerForActivityResult(
            ActivityResultContracts.OpenDocument()) { uri -> handleUri(uri) }
    }

    fun handleUri(uri: Uri?) {
        if (uri == null) return

        try {
            val img = getBase64FromFileUri(uri, requireContext())
            if (img != null) {

                updateLocalImage(img, uri)

                // updateProfileImage(image)
            } else {
                Log.d("myMessage", "Error with the image thingy")
            }
        } catch (e: Exception) {
            logError(e)
            showToast("Error in image conversion", requireContext())
        }
    }

    fun updateLocalImage(image: String, uri: Uri) {
        binding.imgEditProperty.setImageURI(uri)
        if (this.image != null){
            this.image!!.img = image
        } else {
            this.image = PlaceImage(
                -1,
                this.place.id,
                image,
                "Just another image"
            )
        }
    }

    private fun openFileChooser() {
        openFileLauncher.launch(arrayOf("image/*"))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initializeBinding(inflater, container)
        loadData()
        //showData()
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
        val placeId = EditPropertyFragmentArgs.fromBundle(requireArguments()).placeId
        lifecycleScope.launch {
            try {
                val placeWithImage = FirebnbRepository().getPlaceWithImage(placeId)
                this@EditPropertyFragment.place = placeWithImage.place
                this@EditPropertyFragment.image = placeWithImage.image
                showData()
            } catch (e: Exception) {
                logError(e)
                showToast("There was an error", requireContext())
            }
        }
    }

    private fun showData() {
        binding.apply {
            edtPropertyName.setText(place.name)
            edtPropertyType.setText(place.type)
            edtPropertyDescription.setText(place.description)
            edtPropertyPrice.setText(place.price_per_night.toString())
        }
        if (this.image != null && this.image!!.img != null) {
            binding.imgEditProperty.setImage(this.image!!.img)
        }
    }


    private fun initializeEvents() {
        initializeBtnSaveProfile()
        initializeBtnDeleteProperty()
        initializeBtnChooseImage()
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

    private fun initializeBtnDeleteProperty() {
        binding.btnDeleteProperty.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Delete property")
                .setMessage("Are you sure? It will be permanently deleted")
                .setPositiveButton("Yes", { _, _ -> deletePlace()})
                .setNegativeButton("No", null)
                .show()
        }
    }

    private fun deletePlace() {
        lifecycleScope.launch {
            try {
                val success = FirebnbRepository().deletePlace(place.id)
                if (success) {
                    showToast("Property was deleted successfully", requireContext())
                    val navController = findNavController()
                    val action = EditPropertyFragmentDirections.actionEditPropertyFragmentToMyPropertiesFragment()
                    navController.navigate(action)
                } else {
                    showToast("There was an error with the API", requireContext())
                }
            } catch (e: Exception) {
                showToast("There was an error", requireContext())
                logError(e)
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


    private fun initializeBtnChooseImage() {
        binding.btnEditPropertyChooseImage.setOnClickListener {
            openFileChooser()
        }
    }

}