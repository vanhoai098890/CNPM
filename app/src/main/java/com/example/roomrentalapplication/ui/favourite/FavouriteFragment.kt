package com.example.roomrentalapplication.ui.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomrentalapplication.R
import com.example.roomrentalapplication.data.remote.api.model.room.RoomItem
import com.example.roomrentalapplication.databinding.FavouriteFragmentBinding
import com.example.roomrentalapplication.ui.base.BaseFragment
import com.example.roomrentalapplication.ui.room.RoomDialogFragment
import com.example.roomrentalapplication.ui.room.adapter.RoomListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavouriteFragment : BaseFragment() {

    companion object {
        fun newInstance() = FavouriteFragment()
    }

    private val viewModel: FavouriteViewModel by viewModels()
    private lateinit var binding: FavouriteFragmentBinding
    private val roomAdapter: RoomListAdapter by lazy {
        RoomListAdapter().apply {
            onClick = { roomId ->
                showDialog(roomId)
            }
        }
    }

    private fun showDialog(roomItem: RoomItem?) {
        RoomDialogFragment.newInstance(roomItem).show(parentFragmentManager, null)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FavouriteFragmentBinding.inflate(inflater, container, false)
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

    private fun initViews() {
        binding.apply {
            rvFavouriteRoom.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                adapter = roomAdapter
            }
            toolbarId.apply {
                tvCenter.text = context?.getString(R.string.v1_favourite_rooms)
                backButton.visibility = View.INVISIBLE
            }
        }
    }

    private fun initEvents() {
        lifecycleScope.launchWhenStarted {
            launch {
                viewModel.stateListFavouriteRoom.collect {
                    roomAdapter.submitList(it)
                }
            }
            launch {
                viewModel.stateVisibleNotFoundItem.collect {
                    if (it) {
                        binding.layoutNoResult.root.visibility = View.VISIBLE
                    } else {
                        binding.layoutNoResult.root.visibility = View.GONE
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getFavouriteRooms()
    }
}
