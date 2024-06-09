package com.yekutiel.kutiweissyit.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.yekutiel.kutiweissyit.consts.getFixedImageHeight
import com.yekutiel.kutiweissyit.databinding.ImageItemBinding
import com.yekutiel.kutiweissyit.models.Image


class ImagesAdapter(context: Context?, var listener: ImagesAdapterListener?) :
    RecyclerView.Adapter<ImagesAdapter.ImageItemViewHolder>() {

    val fixedImageHeight = getFixedImageHeight(context)
    private var images = ArrayList<Image>()

    fun setImages(images: ArrayList<Image>) {
        this.images = images
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageItemViewHolder {
        val imageItemBinding = ImageItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ImageItemViewHolder(imageItemBinding)
    }

    override fun onBindViewHolder(holder: ImageItemViewHolder, position: Int) {
        holder.bind(images[position])
        loadMoreImages(position)
    }

    private fun loadMoreImages(position: Int) {
        if (position == images.size - 1) {
            listener?.onLoadMoreImages()
        }
    }

    override fun getItemCount(): Int {
        return images.size
    }

    inner class ImageItemViewHolder(private val imageItemBinding: ImageItemBinding) :
        RecyclerView.ViewHolder(imageItemBinding.root) {

        fun bind(image: Image) {
            calculateImageSize(image)

            imageItemBinding.root.setOnClickListener {
                listener?.onImageClick(image)
            }
            Picasso.get().load(image.previewURL).into(imageItemBinding.image)
        }

        private fun calculateImageSize(image: Image) {
            val imageWidth =
                image.previewWidth!! * (fixedImageHeight / image.previewHeight!!)
            imageItemBinding.image.layoutParams.height = fixedImageHeight.toInt()
            imageItemBinding.image.layoutParams.width = imageWidth.toInt()
            imageItemBinding.image.requestLayout()
        }
    }
}

interface ImagesAdapterListener {
    fun onImageClick(image: Image)
    fun onLoadMoreImages()
}