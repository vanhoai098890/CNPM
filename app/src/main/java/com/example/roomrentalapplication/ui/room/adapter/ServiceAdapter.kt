package com.example.roomrentalapplication.ui.room.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.roomrentalapplication.data.remote.api.model.room.ServiceItem
import com.example.roomrentalapplication.databinding.LayoutItemServiceBinding
import com.example.roomrentalapplication.ui.base.BaseListAdapter
import com.example.roomrentalapplication.ui.room.RoomServiceModel
import kotlin.math.max
import kotlin.math.min

class ServiceAdapter : BaseListAdapter<ServiceItem>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseItemViewHolder {
        return ServiceViewHolder(
            LayoutItemServiceBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    inner class ServiceViewHolder(private val binding: LayoutItemServiceBinding) :
        BaseItemViewHolder(binding.root) {
        override fun bind(data: ServiceItem) {
            super.bind(data)
            binding.tvService.text = data.serviceName
            binding.ivService.setImageResource(RoomServiceModel.values()[data.serviceId - 1].iconRes)
        }
    }

    override fun getItemCount(): Int {
        return min(6,currentList.size)
    }

}
