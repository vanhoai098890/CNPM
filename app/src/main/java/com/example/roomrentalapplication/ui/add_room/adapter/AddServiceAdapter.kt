package com.example.roomrentalapplication.ui.add_room.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.roomrentalapplication.databinding.LayoutItemAddServiceBinding
import com.example.roomrentalapplication.extensions.setSafeOnClickListener
import com.example.roomrentalapplication.ui.base.BaseListAdapter
import com.example.roomrentalapplication.ui.room.RoomServiceModel

class AddServiceAdapter : BaseListAdapter<RoomServiceModel>() {

    var onRemoveAction: (Int) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseItemViewHolder {
        return ServiceViewHolder(
            LayoutItemAddServiceBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    inner class ServiceViewHolder(private val binding: LayoutItemAddServiceBinding) :
        BaseItemViewHolder(binding.root) {
        override fun bind(data: RoomServiceModel) {
            super.bind(data)
            binding.tvService.text = data.name.let {
                "${it[0].uppercase()}${it.substring(1).lowercase()}"
            }
            binding.ivService.setImageResource(data.iconRes)
            binding.btnRemove.setSafeOnClickListener {
                onRemoveAction.invoke(adapterPosition)
            }
        }
    }
}
