package com.example.roomrentalapplication.ui.search_main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomrentalapplication.R
import com.example.roomrentalapplication.databinding.FragmentSearchMainBinding
import com.example.roomrentalapplication.extensions.setSafeOnClickListener
import com.example.roomrentalapplication.ui.base.BaseFragment
import com.example.roomrentalapplication.ui.search_main.adapter.HouseTypeAdapter
import com.example.roomrentalapplication.ui.property_fragment.PropertyFragment
import com.example.roomrentalapplication.ui.property_fragment.model.SearchType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchMainFragment : BaseFragment() {
    private val viewModel: SearchMainViewModel by viewModels()
    private var binding: FragmentSearchMainBinding? = null
    private val adapter = HouseTypeAdapter().apply {
        onClick = {
            navigateToPropertyFragment(SearchType.TYPE_PROPERTY, it.id.toString())
        }
    }
    private val citiesAdapter = HouseTypeAdapter().apply {
        onClick = {
            navigateToPropertyFragment(SearchType.CITY, it.name)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_main, container, false)
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initEvents()
    }

    private fun initData() {
        binding?.apply {
            rvFirst.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapter = this@SearchMainFragment.adapter
            }
            rvSecond.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapter = this@SearchMainFragment.citiesAdapter
            }
            ivFilter.setSafeOnClickListener {
                addNoNavigationFragment(
                    PropertyFragment.newInstance(
                        SearchType.NAME,
                        edtSearch.text.toString()
                    )
                )
            }
        }
    }

    private fun initEvents() {
        lifecycleScope.launchWhenStarted {
            launch {
                viewModel.data.collect {
                    adapter.submitList(it)
                }
            }
            launch {
                viewModel.dataCities.collect {
                    citiesAdapter.submitList(it)
                }
            }
        }
    }

    private fun navigateToPropertyFragment(type: SearchType, data: String) {
        addNoNavigationFragment(PropertyFragment.newInstance(type, data))
    }
}
