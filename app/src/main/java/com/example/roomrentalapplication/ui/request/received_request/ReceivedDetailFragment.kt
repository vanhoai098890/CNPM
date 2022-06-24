package com.example.roomrentalapplication.ui.request.received_request

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.roomrentalapplication.data.remote.api.model.received_request.ReceivedRequestItem
import com.example.roomrentalapplication.databinding.FragmentReceivedDetailBinding
import com.example.roomrentalapplication.extensions.setSafeOnClickListener
import com.example.roomrentalapplication.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ReceivedDetailFragment : BaseFragment() {

    private lateinit var binding: FragmentReceivedDetailBinding
    private val viewModel: ReceivedDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReceivedDetailBinding.inflate(inflater, container, false)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            model = viewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initEvents()
    }

    private fun initEvents() {
        arguments?.apply {
            getParcelable<ReceivedRequestItem>(RECEIVED_REQUEST_ITEM)?.apply {
                viewModel.stateReceivedRequestItem.value = this
                binding.toolbarId.tvCenter.text = this.roomName
                viewModel.getReservation(this.reservationId)
                viewModel.getCustomer(this.customerId)
            }
        }
        lifecycleScope.launchWhenStarted {
            launch {
                viewModel.stateSuccess.collect {
                    if (it) {
                        Toast.makeText(requireContext(), "Action succeed!!!", Toast.LENGTH_SHORT).show()
                        viewModel.stateSuccess.value = false
                    }
                }
            }
            launch {
                viewModel.stateError.collect {
                    if (it) {
                        Toast.makeText(requireContext(), "Action failed!!!", Toast.LENGTH_SHORT).show()
                        viewModel.stateError.value = false
                    }
                }
            }
        }
    }

    private fun initViews() {
        binding.apply {
            toolbarId.apply {
                backButton.setSafeOnClickListener {
                    handleBackPressed()
                }
            }
            btnApproved.setSafeOnClickListener {
                // Paying status
                viewModel.updateStatusReservation(3)
            }
            btnCancel.setSafeOnClickListener {
                // Cancel
                viewModel.updateStatusReservation(4)
            }
        }
    }

    companion object {
        private const val RECEIVED_REQUEST_ITEM = "RECEIVED_REQUEST_ITEM"

        @JvmStatic
        fun newInstance(data: ReceivedRequestItem) =
            ReceivedDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(RECEIVED_REQUEST_ITEM, data)
                }
            }
    }
}
