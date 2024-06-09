package com.yekutiel.kutiweissyit.fragments

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.squareup.picasso.Picasso
import com.squareup.picasso.Picasso.LoadedFrom
import com.yekutiel.kutiweissyit.R
import com.yekutiel.kutiweissyit.adapters.PagerViewAdapter
import com.yekutiel.kutiweissyit.adapters.PagerViewAdapterListener
import com.yekutiel.kutiweissyit.databinding.FragmentFullScreenBinding
import com.yekutiel.kutiweissyit.models.Image
import com.yekutiel.kutiweissyit.viewModel.ImagesViewModel

class FullScreenFragment : Fragment(), PagerViewAdapterListener {

    private val viewModel by activityViewModels<ImagesViewModel>()
    private val args: FullScreenFragmentArgs by navArgs()

    private lateinit var binding: FragmentFullScreenBinding
    private var viewPagerAdapter = PagerViewAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFullScreenBinding.inflate(layoutInflater)
        binding.viewPager.adapter = viewPagerAdapter

        viewModel.images.observe(viewLifecycleOwner) {
            viewPagerAdapter.setImages(it)
            val index = getImageIndex(it)
            binding.viewPager.setCurrentItem(index, false)
        }
        return binding.root
    }

    private fun getImageIndex(images: ArrayList<Image>): Int {
        val image = images.find { it.id == args.ImageId }
        return images.indexOf(image)
    }

    override fun onLoadMoreImages() {
        viewModel.getImages()
    }

    override fun onShareImageClick(image: Image) {
        shareImage(image)
    }

    private fun shareImage(image: Image) {
        Picasso.get().load(image.largeImageURL).into(object : com.squareup.picasso.Target {
            override fun onBitmapLoaded(bitmap: Bitmap?, from: LoadedFrom?) {

                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "image/*"
                intent.putExtra(Intent.EXTRA_STREAM, getBitmapFromView(bitmap))
                startActivity(Intent.createChooser(intent, getString(R.string.share_image)))
            }
            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {}
            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}
        })
    }

    fun getBitmapFromView(bitmap:Bitmap?):Uri{
        val bitmapPath = MediaStore.Images.Media.insertImage(context?.contentResolver,
            bitmap,"title", null)
        return Uri.parse(bitmapPath)
    }
}