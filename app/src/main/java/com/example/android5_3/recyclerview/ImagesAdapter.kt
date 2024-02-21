package com.example.android5_3.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.android5_3.data.ImageModel
import com.example.android5_3.databinding.ItemImageBinding

class ImagesAdapter() :
    RecyclerView.Adapter<ImagesAdapter.ViewHolder>() {
    private var imagesList = ArrayList<ImageModel>()
    class ViewHolder(private val binding: ItemImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(image: ImageModel) {
            with(binding) {
                tvLikes.text = "Likes: " + image.likes.toString()
                ivImage.load(image.largeImageURL)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemImageBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(imagesList[position])
    }

    override fun getItemCount(): Int {
        return imagesList.size
    }

    fun updateList(imagesList: ArrayList<ImageModel>, oldCount: Int, imagesListSize: Int) {
        this.imagesList = imagesList
        notifyDataSetChanged()
        notifyItemRangeInserted(oldCount, imagesListSize)
    }
}