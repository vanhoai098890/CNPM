package com.example.roomrentalapplication.ui.property_fragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.roomrentalapplication.data.remote.api.model.property.PropertyItem
import com.example.roomrentalapplication.databinding.LayoutItemRecyclePropertyBinding
import com.example.roomrentalapplication.extensions.setSafeOnClickListener
import com.example.roomrentalapplication.ui.base.BaseListAdapter

class PropertyAdapter : BaseListAdapter<PropertyItem>() {

    var onClick: (PropertyItem) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseItemViewHolder {
        return PropertyTypeViewHolder(
            LayoutItemRecyclePropertyBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    inner class PropertyTypeViewHolder(val binding: LayoutItemRecyclePropertyBinding) :
        BaseItemViewHolder(binding.root) {
        override fun bind(data: PropertyItem) {
            binding.data = data
            if (data.images.isNullOrEmpty()) {
                binding.root.isClickable = false
            } else {
                binding.root.setSafeOnClickListener {
                    onClick.invoke(data)
                }
            }
        }
    }
}
