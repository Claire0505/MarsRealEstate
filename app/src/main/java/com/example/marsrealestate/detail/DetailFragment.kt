package com.example.marsrealestate.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.marsrealestate.R
import com.example.marsrealestate.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val application = requireNotNull(activity).application
        val binding = FragmentDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this

        // MarsProperty從 Safe Args 中獲取選定的對象。
        // 請注意 Kotlin 的非空斷言運算符 ( !!) 的使用。
        // 如果selectedProperty不存在，則發生了可怕的事情，實際上希望代碼拋出一個空指針。
        // （在生產代碼中，您應該以某種方式處理該錯誤。）
        val marsProperty = DetailFragmentArgs.fromBundle(arguments !!).selectedProperty

        // 將使用DetailViewModelFactory來獲取 的實例DetailViewModel。
        // 入門應用程序包含 的實現DetailViewModelFactory，因此您在這裡要做的就是初始化它。
        val viewModelFactory = DetailViewModelFactory(marsProperty, application)

        // 添加DetailViewModel從工廠獲得一個並連接所有部件。
        binding.viewModel = ViewModelProvider(
            this, viewModelFactory).get(DetailViewModel::class.java)

        // Inflate the layout for this fragment
        return binding.root
    }
}