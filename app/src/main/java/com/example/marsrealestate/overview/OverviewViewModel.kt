package com.example.marsrealestate.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marsrealestate.network.MarsAPi
import com.example.marsrealestate.network.MarsProperty
import kotlinx.coroutines.launch


/**
 * The [ViewModel] that is attached to the [OverviewFragment].
 */
// 向視圖模型添加狀態
enum class MarsApiStatus { LOADING, ERROR, DONE}

class OverviewViewModel : ViewModel() {

    private val _status = MutableLiveData<MarsApiStatus>()
    val status : LiveData<MarsApiStatus>
    get() = _status

    // 在內部，我們使用 MutableLiveData，因為我們將更新 MarsProperty 的列表，使用新值
    // properties 是 LiveData並且我們已經為綁定變量設置了生命週期，對它的任何更改都會更新應用程序 UI。
    private val _properties = MutableLiveData<List<MarsProperty>>()
    val properties : LiveData<List<MarsProperty>>
    get() = _properties

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
              _properties.value = MarsAPi.retrofitService.getProperties()
              _status.value = MarsApiStatus.DONE
          } catch (e: Exception) {
              _status.value = MarsApiStatus.ERROR
              // 設置_properties LiveData為空列表。這將清除RecyclerView.
              _properties.value = ArrayList()
          }
      }
    }

}