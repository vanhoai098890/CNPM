package com.example.roomrentalapplication.ui.property_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomrentalapplication.R
import com.example.roomrentalapplication.databinding.FragmentPropertyBinding
import com.example.roomrentalapplication.extensions.setSafeOnClickListener
import com.example.roomrentalapplication.ui.base.BaseFragment
import com.example.roomrentalapplication.ui.property_fragment.adapter.PropertyAdapter
import com.example.roomrentalapplication.ui.property_fragment.detail.PropertyDetailFragment
import com.example.roomrentalapplication.ui.property_fragment.model.SearchType
import com.facebook.shimmer.Shimmer.AlphaHighlightBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class PropertyFragment : BaseFragment() {

    private var binding: FragmentPropertyBinding? = null
    private val viewModel: PropertyViewModel by viewModels()
    private val adapter: PropertyAdapter by lazy {
        PropertyAdapter().apply {
            onClick = {
                replaceNoNavigationFragment(
                    PropertyDetailFragment.newInstance(
                        it.propertyId ?: 0
                    ), true
                )
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_property, container, false)
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
            rvPropertyShimmer.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                adapter = this@PropertyFragment.adapter
            }
            toolbarId.apply {
                backButton.setSafeOnClickListener {
                    handleBackPressed()
                }
                tvCenter.text = context?.getString(R.string.v1_properties)
            }
            arguments?.apply {
                getString(SEARCH_TYPE)?.let { type ->
                    getString(SEARCH_STRING)?.apply {
                        when (type) {
                            SearchType.TYPE_PROPERTY.name -> {
                                try {
                                    viewModel.getProperty(typeId = this.toInt())
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            }
                            SearchType.CITY.name -> {
                                viewModel.getProperty(city = this)
                            }
                            SearchType.NAME.name -> {
                                viewModel.getProperty(name = this)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun initEvents() {
        lifecycleScope.launchWhenStarted {
            launch {
                viewModel.allPropertyItem.collect {
                    adapter.submitList(it)
                }
            }
            launch {
                viewModel.loadingState().collect {
                    binding?.apply {
                        if (it) {
                            shimmerLayout.startShimmer()
                        } else {
                            shimmerLayout.setShimmer(
                                AlphaHighlightBuilder()
                                    .setBaseAlpha(1f)
                                    .setIntensity(0f)
                                    .build()
                            )
                            shimmerLayout.hideShimmer()
                            shimmerLayout.stopShimmer()
                            shimmerLayout.clearAnimation()
                        }
                    }
                }
            }
        }
    }

    companion object {
        private const val SEARCH_STRING = "SEARCH_STRING"
        private const val SEARCH_TYPE = "SEARCH_TYPE"

        @JvmStatic
        fun newInstance(searchType: SearchType, searchString: String) =
            PropertyFragment().apply {
                arguments = Bundle().apply {
                    putString(SEARCH_STRING, searchString)
                    putString(SEARCH_TYPE, searchType.name)
                }
            }
    }
}
