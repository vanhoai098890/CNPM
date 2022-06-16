package com.example.roomrentalapplication.ui.nationality.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.roomrentalapplication.databinding.LayoutItemNationalityBinding
import com.example.roomrentalapplication.ui.base.BaseListAdapter
import com.example.roomrentalapplication.ui.nationality.model.NationalityEnum

class NationalityAdapter : BaseListAdapter<NationalityEnum>() {

    var pastItem: NationalityEnum? = null
    var currentPosition: NationalityEnum? = null
    var onItemClick: (NationalityEnum) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseItemViewHolder {
        return ItemViewHolder(
            LayoutItemNationalityBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    inner class ItemViewHolder(private var binding: LayoutItemNationalityBinding) :
        BaseItemViewHolder(binding.root) {
        override fun bind(data: NationalityEnum) {
            super.bind(data)
            binding.tvNation.isSelected = data == currentPosition
            binding.tvNation.text = binding.root.resources.getString(data.resString)
            data.position = adapterPosition
            binding.root.setOnClickListener {
                if (currentPosition != data) {
                    pastItem = currentPosition
                    currentPosition = data
                    pastItem?.apply {
                        notifyItemChanged(position)
                    }
                    currentPosition?.apply {
                        notifyItemChanged(position)
                    }
                    onItemClick.invoke(data)
                }
            }
        }
    }
}
