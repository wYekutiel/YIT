package com.yekutiel.kutiweissyit.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.yekutiel.kutiweissyit.databinding.PagerItemBinding
import com.yekutiel.kutiweissyit.models.Image
import java.lang.Exception

class PagerViewAdapter(val listener: PagerViewAdapterListener?) :
    RecyclerView.Adapter<PagerViewAdapter.PagerViewHolder>() {

    private var images: List<Image> = ArrayList()

    fun setImages(images: ArrayList<Image>) {
        this.images = images
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val pagerItemBinding = PagerItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return PagerViewHolder(pagerItemBinding)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
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

    inner class PagerViewHolder(private val pagerItemBinding: PagerItemBinding) :
        RecyclerView.ViewHolder(pagerItemBinding.root) {
        fun bind(image: Image) {
            pagerItemBinding.progress.visibility = View.VISIBLE
            Picasso.get().load(image.largeImageURL).into(pagerItemBinding.image, object : Callback {
                override fun onSuccess() {
                    pagerItemBinding.progress.visibility = View.GONE
                }
                override fun onError(e: Exception?) {}
            })

            pagerItemBinding.share.setOnClickListener {
                listener?.onShareImageClick(image)
            }
        }
    }
}

interface PagerViewAdapterListener {
    fun onLoadMoreImages()
    fun onShareImageClick(image: Image)
}