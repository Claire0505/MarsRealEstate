package com.example.marsrealestate.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.marsrealestate.network.MarsAPi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * The [ViewModel] that is attached to the [OverviewFragment].
 */
class OverviewViewModel : ViewModel() {

    // The internal MutableLiveData String that stores the most recent response
    private val _response = MutableLiveData<String>()

    // The external immutable LiveData for the response String
    // response 是 LiveData並且我們已經為綁定變量設置了生命週期，對它的任何更改都會更新應用程序 UI。
    val response : LiveData<String>
    get() = _response

    /**
     * Call getMarsRealEstateProperties() on init so we can display status immediately.
     */
    init {
        getMarsRealEstateProperties()
    }

    /**
     * Sets the value of the status LiveData to the Mars API status.
     * 將調用 Retrofit 服務並處理返回的 JSON 字符串的方法，導入retrofit2.Callback
     * 返回一個Call對象。然後您可以調用該enqueue()對像以在後台線程上啟動網絡請求。
     *
     * _response是LiveData，它決定了在文本視圖中顯示的字符串中。每個狀態都需要更新_response LiveData.
     */
    private fun getMarsRealEstateProperties() {
        MarsAPi.retrofitService.getProperties().enqueue(
            object: Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    _response.value = response.body()
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    _response.value = "Failure: " + t.message
                }

            })
    }

}