package com.example.roomrentalapplication.ui.room

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.example.roomrentalapplication.R
import com.example.roomrentalapplication.data.AppConstant
import com.example.roomrentalapplication.data.remote.api.model.room.RoomItem
import com.example.roomrentalapplication.databinding.LayoutRoomDialogBinding
import com.example.roomrentalapplication.extensions.setSafeOnClickListener
import com.example.roomrentalapplication.ui.base.BaseDialogFragment
import com.example.roomrentalapplication.ui.base.BaseFragment
import com.example.roomrentalapplication.ui.common.ImageFragment
import com.example.roomrentalapplication.ui.property_fragment.adapter.PropertyImageAdapter
import com.example.roomrentalapplication.ui.property_fragment.detail.PropertyDetailFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class RoomDialogFragment : BaseDialogFragment() {

    private val myViewModel: RoomDialogViewModel by viewModels()
    private lateinit var binding: LayoutRoomDialogBinding
    private lateinit var animSelect: Animation
    private var totalSizeImage: Int? = null

    override fun setContentDialog(dialog: Dialog) {
        dialog.apply {
            binding = LayoutRoomDialogBinding.inflate(LayoutInflater.from(context))
            setContentView(binding.root)
            animSelect =
                AnimationUtils.loadAnimation(requireContext(), R.anim.anim_select_favourite)
        }
    }

    override fun initListeners(dialog: Dialog) {
        arguments?.apply {
            this.getParcelable<RoomItem>(ROOM)?.let { roomItem ->
                binding.apply {
                    data = roomItem
                    viewModel = myViewModel
                    totalSizeImage = roomItem.images?.size
                    vpProperty.adapter = PropertyImageAdapter(
                        requireParentFragment(),
                        (parentFragment as? BaseFragment)?.level ?: 1,
                        roomItem.images?.map {
                            return@map ImageFragment.newInstance(it.url ?: "")
                        } ?: mutableListOf()
                    )
                    vpProperty.apply {
                        registerOnPageChangeCallback(object :
                            ViewPager2.OnPageChangeCallback() {
                            override fun onPageSelected(position: Int) {
                                super.onPageSelected(position)
                                tvImageCurrent.text =
                                    getString(
                                        R.string.v1_page_current_total,
                                        position + 1,
                                        totalSizeImage
                                    )
                            }
                        })
                        setPageTransformer(AppConstant.COMPOSITE_VIEWPAGER)
                        offscreenPageLimit = 3
                        clipChildren = false
                        clipToPadding = false
                    }
                    ivFavourite.setSafeOnClickListener {
                        if (!ivFavourite.isSelected) {
                            ivFavourite.startAnimation(animSelect)
                            myViewModel.saveRoom(roomId = roomItem.roomId)
                        } else {
                            myViewModel.deleteRoomSaved(roomId = roomItem.roomId)
                        }
                        ivFavourite.isSelected = !ivFavourite.isSelected
                    }
                    ivBack.setSafeOnClickListener {
                        dismiss()
                    }
                    myViewModel.getStatusSaved(roomItem.roomId)
                    btnBook.setSafeOnClickListener {
                        dismiss()
                        newInstance(roomItem).show(parentFragmentManager, null)
                    }
                }
                lifecycleScope.launchWhenStarted {
                    launch {
                        myViewModel.stateFavourite.collect {
                            binding.ivFavourite.isSelected = it
                        }
                    }
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            launch {
                myViewModel.stateErrorCustomerId.collect {
                    if (it) {
                        Toast.makeText(
                            requireContext(),
                            "Something wrong when get customerId",
                            Toast.LENGTH_SHORT
                        ).show()
                        myViewModel.stateErrorCustomerId.value = false
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
            setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
            )
        }
    }

    companion object {

        private const val ROOM = "ROOM"

        fun newInstance(room: RoomItem?): RoomDialogFragment {
            val args = Bundle()
            args.putParcelable(ROOM, room)
            val fragment = RoomDialogFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
