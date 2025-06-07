package com.example.firebnb.view.rentingsRecyclerView

import androidx.recyclerview.widget.RecyclerView
import com.example.firebnb.databinding.ItemRentingPreviewBinding
import com.example.firebnb.model.api.responses.RentingPreview

class RentingPreviewHolder(var binding: ItemRentingPreviewBinding): RecyclerView.ViewHolder(binding.root) {

    lateinit var rentingPreview: RentingPreview

    fun linkPreview(preview: RentingPreview) {
        this.rentingPreview = preview

        binding.apply {
            txtRentingPreviewName.setText(rentingPreview.name)
            txtRentingPreviewType.setText(rentingPreview.type)
            txtRentingPreviewPrice.setText(rentingPreview.total_price.toString() + "$")
        }
    }

}