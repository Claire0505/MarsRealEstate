package com.example.marsrealestate.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marsrealestate.network.MarsAPi
import com.example.marsrealestate.network.MarsApiFilter
import com.example.marsrealestate.network.MarsProperty
import kotlinx.coroutines.launch


/**
 * The [ViewModel] that is attached to the [OverviewFragment].
 */
// 向視圖模型添加狀態
enum class MarsApiStatus { LOADING, ERROR, DONE }

class OverviewViewModel : ViewModel() {

    private val _status = MutableLiveData<MarsApiStatus>()
    val status: LiveData<MarsApiStatus>
        get() = _status

    // 在內部，我們使用 MutableLiveData，因為我們將更新 MarsProperty 的列表，使用新值
    // properties 是 LiveData並且我們已經為綁定變量設置了生命週期，對它的任何更改都會更新應用程序 UI。
    private val _properties = MutableLiveData<List<MarsProperty>>()
    val properties: LiveData<List<MarsProperty>>
        get() = _properties

    // 當用戶點擊概覽模型中的照片時，它應該觸發導航到一個片段，該片段顯示有關單擊項目的詳細信息。
    // LiveData to handle navigation to the selected property
    private val _navigateToSelectedProperty = MutableLiveData<MarsProperty>()
    val navigateToSelectedProperty: LiveData<MarsProperty>
        get() = _navigateToSelectedProperty

    /**
     * Call getMarsRealEstateProperties() on init so we can display status immediately.
     */
    init {
        getMarsRealEstateProperties(MarsApiFilter.SHOW_ALL)
    }

    /**
     * Sets the value of the status LiveData to the Mars API status.
     *  ViewModelScope是為ViewModel應用程序中的每個定義的內置協程範圍。
     *  如果ViewModel清除了 ，則在此範圍內啟動的任何協程都會自動取消。
     * _response是LiveData，它決定了在文本視圖中顯示的字符串中。每個狀態都需要更新_response LiveData.
     */
    private fun getMarsRealEstateProperties(filter: MarsApiFilter) {
        viewModelScope.launch {
            try {
                _properties.value = MarsAPi.retrofitService.getProperties(filter.value)
                _status.value = MarsApiStatus.DONE
            } catch (e: Exception) {
                _status.value = MarsApiStatus.ERROR
                // 設置_properties LiveData為空列表。這將清除RecyclerView.
                _properties.value = ArrayList()
            }
        }
    }

    /**
     * Updates the data set filter for the web services by querying the data with the new filter
     * by calling [getMarsRealEstateProperties]
     * @param filter the [MarsApiFilter] that is sent as part of the web server request
     */
    fun updateFilter(filter: MarsApiFilter) {
        getMarsRealEstateProperties(filter)
    }

    /**
     * When the property is clicked, set the [_navigateToSelectedProperty] [MutableLiveData]
     * @param marsProperty The [MarsProperty] that was clicked on.
     */
    fun displayPropertyDetails(marsProperty: MarsProperty){
        _navigateToSelectedProperty.value = marsProperty
    }

    /**
     * After the navigation has taken place, make sure navigateToSelectedProperty is set to null
     * 添加將的值歸零的方法displayPropertyDetailsComplete()
     * _navigateToSelectedProperty。您需要它來標記導航狀態完成，並避免在用戶從詳細信息視圖返回時再次觸發導航。
     */
    fun displayPropertyDetailsComplete() {
        _navigateToSelectedProperty.value = null
    }

}