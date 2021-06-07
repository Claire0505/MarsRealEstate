package com.example.marsrealestate.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marsrealestate.network.MarsAPi
import com.example.marsrealestate.network.MarsProperty
import kotlinx.coroutines.launch
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
    val response: LiveData<String>
        get() = _response

    /**
     * Call getMarsRealEstateProperties() on init so we can display status immediately.
     */
    init {
        getMarsRealEstateProperties()
    }

    /**
     * Sets the value of the status LiveData to the Mars API status.
     *  ViewModelScope是為ViewModel應用程序中的每個定義的內置協程範圍。
     *  如果ViewModel清除了 ，則在此範圍內啟動的任何協程都會自動取消。
     * _response是LiveData，它決定了在文本視圖中顯示的字符串中。每個狀態都需要更新_response LiveData.
     */
    private fun getMarsRealEstateProperties() {
      viewModelScope.launch {
          try {
              // 調用getProperties()從MarsApi服務創建並啟動在後台線程網絡通話。
              val listResult = MarsAPi.retrofitService.getProperties()
              _response.value = "Success: ${listResult.size} Mars properties retrieved"

          }catch (e: Exception){
              _response.value = "Failure: ${e.message}"
          }

      }
    }

}