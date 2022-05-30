package com.example.roomrentalapplication.ui.property_fragment.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.CompositePageTransformer
import com.example.roomrentalapplication.R
import com.example.roomrentalapplication.databinding.PropertyDetailFragmentBinding
import com.example.roomrentalapplication.extensions.onSuccess
import com.example.roomrentalapplication.extensions.setSafeOnClickListener
import com.example.roomrentalapplication.ui.base.BaseFragment
import com.example.roomrentalapplication.ui.common.ImageFragment
import com.example.roomrentalapplication.ui.property_fragment.adapter.PropertyImageAdapter
import com.example.roomrentalapplication.ui.room.RoomDialogFragment
import com.example.roomrentalapplication.ui.room.adapter.RoomListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch


@AndroidEntryPoint
class PropertyDetailFragment : BaseFragment() {

    companion object {
        private const val PROPERTY_ID = "PROPERTY_ID"
        fun newInstance(id: Int) = PropertyDetailFragment().apply {
            arguments = Bundle().apply {
                putInt(PROPERTY_ID, id)
            }
        }
    }

    private lateinit var binding: PropertyDetailFragmentBinding
    private lateinit var imageAdapter: PropertyImageAdapter
    private val viewModel: PropertyDetailViewModel by viewModels()
    private val roomAdapter: RoomListAdapter by lazy {
        RoomListAdapter().apply {
            onClick = { roomId ->
                showDialog(roomId)
            }
        }
    }
    private val roomDialog: RoomDialogFragment by lazy {
        RoomDialogFragment.newInstance(1)
    }

    private fun showDialog(roomId: Int?) {
        roomDialog.show(parentFragmentManager, null)
    }

    private val composite = CompositePageTransformer().apply {
        addTransformer { page, position ->
            val r = 1 - kotlin.math.abs(position)
            page.scaleY = 0.65f + r * 0.35f
            page.scaleX = 0.65f + r * 0.35f
            page.alpha = 0.4f + r * 0.6f
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PropertyDetailFragmentBinding.inflate(inflater, container, false)
        binding.apply {
            data = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        initViews()
        initEvents()
        return binding.root
    }

    private fun initViews() {
        binding.apply {
            vpProperty.apply {
                setPageTransformer(composite)
                offscreenPageLimit = 3
                clipChildren = false
                clipToPadding = false
            }
            toolbarId.apply {
                tvCenter.text = getString(R.string.v1_details)
                backButton.setSafeOnClickListener {
                    handleBackPressed()
                }
            }
            ivMess.setSafeOnClickListener {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.fromParts("sms", viewModel.stateCustomerData.value.phoneNumber, null)
                    )
                )
            }
            ivPhone.setSafeOnClickListener {
                startActivity(
                    Intent(
                        Intent.ACTION_DIAL,
                        Uri.parse("tel: ${viewModel.stateCustomerData.value.phoneNumber}")
                    )
                )
            }
            ivAddress.setSafeOnClickListener {
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("http://maps.google.com/maps?daddr=${viewModel.data.value.lon},${viewModel.data.value.lat}")
                )
                startActivity(intent)
            }
            rvRoom.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                adapter = roomAdapter
            }
        }
    }

    private fun initEvents() {
        arguments?.apply {
            getInt(PROPERTY_ID).let { propertyId ->
                lifecycleScope.launchWhenStarted {
                    launch {
                        viewModel.getRoomByPropertyId(propertyId).onSuccess {
                            roomAdapter.submitList(it.data)
                        }.launchIn(lifecycleScope)
                    }
                    launch {
                        viewModel.getPropertyDetail(propertyId)
                    }
                    launch {
                        viewModel.listImageProperty.collect {
                            imageAdapter = PropertyImageAdapter(
                                this@PropertyDetailFragment, level,
                                it.map { imageItem ->
                                    ImageFragment.newInstance(imageItem.url ?: "")
                                })
                            binding.vpProperty.adapter = imageAdapter
                            binding.indicatorLayout.setViewPager(binding.vpProperty)
                        }
                    }
                }
            }
        }
    }
}
