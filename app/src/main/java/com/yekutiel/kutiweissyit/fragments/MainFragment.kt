package com.yekutiel.kutiweissyit.fragments

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.yekutiel.kutiweissyit.R
import com.yekutiel.kutiweissyit.adapters.ImagesAdapter
import com.yekutiel.kutiweissyit.adapters.ImagesAdapterListener
import com.yekutiel.kutiweissyit.databinding.FragmentMainBinding
import com.yekutiel.kutiweissyit.models.Image
import com.yekutiel.kutiweissyit.sharedPreferences.SharedPreferencesManager
import com.yekutiel.kutiweissyit.viewModel.ImagesViewModel


class MainFragment : Fragment(), ImagesAdapterListener {

    private val viewModel by activityViewModels<ImagesViewModel>()
    private val adapter = ImagesAdapter(context, this)
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater)

        initViewModel()
        initLastSearch()
        initAdapter()
        initOnClick()

        return binding.root
    }

    private fun initViewModel() {
        viewModel.images.observe(viewLifecycleOwner) {
            adapter.setImages(it)
        }

        viewModel.displayProgress.observe(viewLifecycleOwner) {
            binding.progress.visibility = if (it) View.VISIBLE else View.GONE
        }
    }

    private fun initLastSearch() {
        val lastSearchText = SharedPreferencesManager(context).getLastSearchText()
        binding.searchText.setText(lastSearchText)
    }

    private fun initAdapter() {
        val layoutManager = FlexboxLayoutManager(context)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.FLEX_START
        binding.rv.layoutManager = layoutManager
        binding.rv.adapter = adapter
    }

    private fun initOnClick() {
        binding.searchButton.setOnClickListener {
            viewModel.clearOldData()
            viewModel.searchText.value = binding.searchText.text.toString()

            if (viewModel.searchText.value.isNullOrEmpty()) {
                Toast.makeText(context, getString(R.string.enter_text), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            SharedPreferencesManager(context).saveLastSearchText(viewModel.searchText.value)
            viewModel.getImages()

            hideKeyboard(context, binding.searchText)
        }
    }

    private fun hideKeyboard(context: Context?, view: View) {
        val imm = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onImageClick(image: Image) {
        image.id?.let {
            val action = MainFragmentDirections.actionMainFragmentToFullScreenFragment(it)
            findNavController().navigate(action)
        }
    }

    override fun onLoadMoreImages() {
        viewModel.getImages()
    }
}