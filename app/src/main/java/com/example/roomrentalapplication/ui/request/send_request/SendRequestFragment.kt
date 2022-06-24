package com.example.roomrentalapplication.ui.request.send_request

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomrentalapplication.databinding.FragmentSendRequestBinding
import com.example.roomrentalapplication.ui.base.BaseFragment
import com.example.roomrentalapplication.ui.request.adapter.ReceivedRequestAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SendRequestFragment : BaseFragment() {

    private lateinit var binding: FragmentSendRequestBinding
    private val viewModel: SendRequestViewModel by viewModels()
    private val mAdapter: ReceivedRequestAdapter by lazy {
        ReceivedRequestAdapter().apply {
            onClickItem = {
                PayingDialog.newInstance(it).show(parentFragmentManager, null)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSendRequestBinding.inflate(inflater, container, false)
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

    private fun initViews() {
        binding.apply {
            rvSendRequest.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                adapter = mAdapter
            }
        }
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launchWhenStarted {
            launch {
                viewModel.getSendReservation()
            }
        }
    }
}
