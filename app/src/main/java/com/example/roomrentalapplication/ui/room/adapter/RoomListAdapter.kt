package com.example.roomrentalapplication.ui.room.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.roomrentalapplication.data.remote.api.model.room.RoomItem
import com.example.roomrentalapplication.databinding.ItemLayoutRoomBinding
import com.example.roomrentalapplication.extensions.setSafeOnClickListener
import com.example.roomrentalapplication.ui.base.BaseListAdapter

class RoomListAdapter : BaseListAdapter<RoomItem>() {

    var onClick: (Int?) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseItemViewHolder {
        return RoomViewHolder(
            ItemLayoutRoomBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    inner class RoomViewHolder(private val binding: ItemLayoutRoomBinding) :
        BaseItemViewHolder(binding.root) {
        override fun bind(data: RoomItem) {
            binding.data = data
            binding.root.setSafeOnClickListener {
                onClick(data.roomId)
            }
        }
    }
}
