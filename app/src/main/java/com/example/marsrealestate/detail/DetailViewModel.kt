package com.example.marsrealestate.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.marsrealestate.network.MarsProperty

/**
 *  The [ViewModel] associated with the [DetailFragment], containing information about the selected
 *  [MarsProperty].
 */
class DetailViewModel(marsProperty: MarsProperty, app: Application) : AndroidViewModel(app) {

    // 在類定義中，LiveData為選定的 Mars 屬性添加，以將該信息公開給詳細信息視圖。
    // 創建一個MutableLiveData來保存它MarsProperty本身，然後公開一個不可變的公共LiveData屬性。
    private val _selectedProperty = MutableLiveData<MarsProperty>()
    val selectedProperty: LiveData<MarsProperty>
        get() = _selectedProperty

    // Initialize the _selectedProperty MutableLiveData
    init {
        _selectedProperty.value = marsProperty
    }

}