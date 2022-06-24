package com.example.roomrentalapplication.ui.request.received_request

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomrentalapplication.databinding.FragmentReceivedRequestBinding
import com.example.roomrentalapplication.ui.base.BaseFragment
import com.example.roomrentalapplication.ui.request.adapter.ReceivedRequestAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ReceivedRequestFragment : BaseFragment() {

    private lateinit var binding: FragmentReceivedRequestBinding
    private val viewModel: ReceivedRequestViewModel by viewModels()
    private val mAdapter: ReceivedRequestAdapter by lazy {
        ReceivedRequestAdapter().apply {
            onClickItem = { data ->
                addNoNavigationFragment(ReceivedDetailFragment.newInstance(data))
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReceivedRequestBinding.inflate(inflater, container, false)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initEvents()
    }

    private fun initEvents() {
        lifecycleScope.launchWhenStarted {
            launch {
                viewModel.stateVisibleNotFound.collect {
                    if (it) {
                        binding.layoutNoResult.root.visibility = View.VISIBLE
                    } else {
                        binding.layoutNoResult.root.visibility = View.GONE
                    }
                }
            }
            launch {
                viewModel.stateListItem.collect {
                    mAdapter.submitList(it)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launchWhenStarted {
            launch {
                viewModel.getReceivedReservation()
            }
        }
    }

    private fun initViews() {
        binding.apply {
            rvReceivedRequest.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                adapter = mAdapter
            }
        }
    }
}
