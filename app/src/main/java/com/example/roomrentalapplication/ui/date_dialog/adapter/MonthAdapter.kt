package com.example.roomrentalapplication.ui.date_dialog.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.roomrentalapplication.databinding.LayoutItemDateBinding
import com.example.roomrentalapplication.databinding.LayoutItemDateTitleBinding
import com.example.roomrentalapplication.ui.base.BaseListAdapter
import com.example.roomrentalapplication.ui.date_dialog.model.ItemDate

class MonthAdapter : BaseListAdapter<ItemDate>() {

    companion object {
        const val TITLE = 0
        const val DATE = 1
    }

    var onClick: (ItemDate) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseItemViewHolder {
        when (viewType) {
            TITLE -> return TitleViewHolder(
                LayoutItemDateTitleBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> return ItemDateViewHolder(
                LayoutItemDateBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position).isTitle) {
            true -> TITLE
            else -> DATE
        }
    }

    inner class ItemDateViewHolder(private val binding: LayoutItemDateBinding) :
        BaseItemViewHolder(binding.root) {

        override fun bind(data: ItemDate) {
            if (data.numberDate.toInt() in 1..31) {
                binding.tvDate.text = data.numberDate
                binding.root.isSelected = data.isSelected
                binding.root.isEnabled = data.isEnable
                binding.root.setOnClickListener {
                    onClick.invoke(data)
                }
            }
        }
    }

    inner class TitleViewHolder(private val binding: LayoutItemDateTitleBinding) :
        BaseItemViewHolder(binding.root) {
        override fun bind(data: ItemDate) {
            binding.tvTitle.text = data.numberDate
        }
    }
}
