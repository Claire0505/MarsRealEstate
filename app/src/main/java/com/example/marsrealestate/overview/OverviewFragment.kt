package com.example.marsrealestate.overview

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.marsrealestate.R
import com.example.marsrealestate.databinding.FragmentOverviewBinding

class OverviewFragment : Fragment() {

    /**
     * Lazily initialize our [OverviewViewModel].
     *  延遲初始化我們的 [OverviewViewModel]，需要使用時才會用到
     */
    private val viewModel : OverviewViewModel by lazy {
        ViewModelProvider(this).get(OverviewViewModel::class.java)
    }

    /**
     * Inflates the layout with Data Binding, sets its lifecycle owner to the OverviewFragment
     * to enable Data Binding to observe LiveData, and sets up the RecyclerView with an adapter.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentOverviewBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        // 已經設置了生命週期所有者，任何LiveData在數據綁定中使用的都將自動觀察到任何更改，並且 UI 將相應地更新。
        binding.lifecycleOwner = this

        // Giving the binding access to the OverviewViewModel
        binding.viewModel = viewModel

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

}