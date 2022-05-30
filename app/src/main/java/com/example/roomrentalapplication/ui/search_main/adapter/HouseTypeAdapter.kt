package com.example.roomrentalapplication.ui.search_main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.roomrentalapplication.databinding.LayoutItemRecycleBinding
import com.example.roomrentalapplication.databinding.LayoutItemRecycleCitiesBinding
import com.example.roomrentalapplication.extensions.setSafeOnClickListener
import com.example.roomrentalapplication.ui.base.BaseListAdapter

class HouseTypeAdapter : BaseListAdapter<ItemData>() {

    var onClick: (ItemData) -> Unit = {}

    companion object {
        const val HOUSE_TYPE = 0
        const val CITIES_TYPE = 1
    }

    override fun getItemViewType(position: Int): Int {
        val temp = currentList[position]
        if (temp.details.isNotBlank()) {
            return HOUSE_TYPE
        }
        return CITIES_TYPE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseItemViewHolder {
        return when (viewType) {
            HOUSE_TYPE -> {
                HouseTypeViewHolder(
                    LayoutItemRecycleBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> {
                CitiesTypeViewHolder(
                    LayoutItemRecycleCitiesBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }

    inner class HouseTypeViewHolder(val binding: LayoutItemRecycleBinding) :
        BaseItemViewHolder(binding.root) {
        override fun bind(data: ItemData) {
            binding.data = data
            binding.root.setSafeOnClickListener {
                onClick.invoke(data)
            }
        }
    }

    inner class CitiesTypeViewHolder(val binding: LayoutItemRecycleCitiesBinding) :
        BaseItemViewHolder(binding.root) {
        override fun bind(data: ItemData) {
            binding.data = data
            binding.root.setSafeOnClickListener {
                onClick.invoke(data)
            }
        }
    }
}
