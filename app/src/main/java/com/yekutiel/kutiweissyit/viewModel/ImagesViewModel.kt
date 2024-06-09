package com.yekutiel.kutiweissyit.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yekutiel.kutiweissyit.models.Image
import com.yekutiel.kutiweissyit.requestManager.RequestManager
import com.yekutiel.kutiweissyit.models.ImagesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ImagesViewModel : ViewModel() {
    private val pageNumber = MutableLiveData(0)
    val totalHits = MutableLiveData(0)
    val displayProgress = MutableLiveData(false)
    val searchText = MutableLiveData("")
    val images: MutableLiveData<ArrayList<Image>> = MutableLiveData()

    fun getImages() {
        if ((images.value?.size ?: 0) > totalHits.value!!) return

        pageNumber.value = pageNumber.value?.plus(1)
        displayProgress.value = true

        val call = RequestManager.getInstance()?.imagesService?.getImages(
            searchText.value, pageNumber.value ?: 1
        )
        call?.enqueue(object : Callback<ImagesResponse> {
            override fun onResponse(
                call: Call<ImagesResponse>,
                response: Response<ImagesResponse>
            ) {
                displayProgress.value = false

                response.body()?.let {
                    totalHits.value = it.totalHits
                    images.value?.addAll(it.images)
                    images.value = images.value
                }
            }

            override fun onFailure(call: Call<ImagesResponse>, t: Throwable) {
                displayProgress.value = false
            }
        })
    }

    fun clearOldData() {
        pageNumber.value = 0
        totalHits.value = 0
        images.value = ArrayList()
    }
}