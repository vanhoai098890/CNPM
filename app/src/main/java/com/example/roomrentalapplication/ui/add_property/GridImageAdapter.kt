package com.example.roomrentalapplication.ui.add_property

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.roomrentalapplication.databinding.LayoutItemAddImageBinding
import com.example.roomrentalapplication.databinding.LayoutItemButtonAddImageBinding
import com.example.roomrentalapplication.extensions.setSafeOnClickListener
import com.example.roomrentalapplication.ui.base.BaseListAdapter

class GridImageAdapter : BaseListAdapter<Uri?>() {

    companion object {
        const val BUTTON_TYPE = 0
        const val IMAGE_TYPE = 1
    }

    var onAddAction: () -> Unit = {}
    var onRemoveAction: (Int) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseItemViewHolder {
        return when (viewType) {
            IMAGE_TYPE -> {
                ImageViewHolder(
                    LayoutItemAddImageBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> {
                AddButtonViewHolder(
                    LayoutItemButtonAddImageBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (getItem(position) == null) {
            return BUTTON_TYPE
        }
        return IMAGE_TYPE
    }

    inner class ImageViewHolder(private val binding: LayoutItemAddImageBinding) :
        BaseItemViewHolder(binding.root) {
        override fun bind(data: Uri?) {
            binding.apply {
                ivImage.apply {
                    setImageURI(data)
                }
                btnRemove.visibility = View.VISIBLE
                btnRemove.setSafeOnClickListener {
                    onRemoveAction.invoke(adapterPosition)
                }
            }
        }
    }

    inner class AddButtonViewHolder(private val binding: LayoutItemButtonAddImageBinding) :
        BaseItemViewHolder(binding.root) {
        override fun bind(data: Uri?) {
            binding.apply {
                btnAdd.setSafeOnClickListener {
                    onAddAction.invoke()
                }
            }
        }
    }
}
