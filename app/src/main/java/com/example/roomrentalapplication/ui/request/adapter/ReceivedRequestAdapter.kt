package com.example.roomrentalapplication.ui.request.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.roomrentalapplication.data.remote.api.model.received_request.ReceivedRequestItem
import com.example.roomrentalapplication.databinding.LayoutItemReceivedRequestBinding
import com.example.roomrentalapplication.extensions.setSafeOnClickListener
import com.example.roomrentalapplication.ui.base.BaseListAdapter

class ReceivedRequestAdapter : BaseListAdapter<ReceivedRequestItem>() {

    var onClickItem: (ReceivedRequestItem) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseItemViewHolder {
        return ReceivedRequestViewHolder(
            LayoutItemReceivedRequestBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    inner class ReceivedRequestViewHolder(val binding: LayoutItemReceivedRequestBinding) :
        BaseItemViewHolder(binding.root) {
        override fun bind(data: ReceivedRequestItem) {
            binding.data = data
            binding.root.setSafeOnClickListener {
                onClickItem.invoke(data)
            }
        }
    }
}
