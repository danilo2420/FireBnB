package com.example.firebnb.view.rentingsRecyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.firebnb.databinding.ItemPlaceBinding
import com.example.firebnb.databinding.ItemRentingPreviewBinding
import com.example.firebnb.model.Place
import com.example.firebnb.model.api.responses.RentingPreview
import com.example.firebnb.view.homeRecyclerView.PlaceHolder

class RentingPreviewAdapter(
    var previews: List<RentingPreview>,
    val lambda: (RentingPreviewHolder) -> Unit
): RecyclerView.Adapter<RentingPreviewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RentingPreviewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRentingPreviewBinding.inflate(inflater, parent, false)
        return RentingPreviewHolder(binding)
    }

    override fun getItemCount(): Int = previews.size

    override fun onBindViewHolder(holder: RentingPreviewHolder, position: Int) {
        val preview = previews.get(position)
        holder.linkPreview(preview)
        holder.binding.root.setOnClickListener{
            lambda(holder)
        }
    }

    fun setPreviewList(p: List<RentingPreview>) {
        this.previews = p
        notifyDataSetChanged()
    }

}