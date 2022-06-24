package com.example.roomrentalapplication.ui.request

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.roomrentalapplication.R
import com.example.roomrentalapplication.databinding.FragmentRequestBinding
import com.example.roomrentalapplication.extensions.setSafeOnClickListener
import com.example.roomrentalapplication.ui.base.BaseFragment
import com.example.roomrentalapplication.ui.request.adapter.RequestAdapter
import com.example.roomrentalapplication.ui.request.received_request.ReceivedRequestFragment
import com.example.roomrentalapplication.ui.request.send_request.SendRequestFragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RequestFragment : BaseFragment() {

    private lateinit var binding: FragmentRequestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRequestBinding.inflate(inflater, container, false)
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

    }

    private fun initViews() {
        binding.apply {
            toolbarId.apply {
                tvCenter.text = getString(R.string.v1_requests)
                backButton.setSafeOnClickListener {
                    handleBackPressed()
                }
            }
            vpRequest.adapter = RequestAdapter(
                this@RequestFragment,
                level,
                listOf(SendRequestFragment(), ReceivedRequestFragment())
            )
            vpRequest.offscreenPageLimit = 1
            vpRequest.isUserInputEnabled = false
            TabLayoutMediator(tabContainer, vpRequest) { tab, position ->
                if (position == 0) {
                    tab.text = getString(R.string.v1_send)
                } else {
                    tab.text = getString(R.string.v1_received)
                }
            }.attach()
        }
    }
}
