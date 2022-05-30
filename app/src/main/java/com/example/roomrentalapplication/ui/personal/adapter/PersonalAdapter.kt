package com.example.roomrentalapplication.ui.personal.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.roomrentalapplication.R
import com.example.roomrentalapplication.data.AppConstant
import com.example.roomrentalapplication.databinding.ItemPersonalContentFunctionBinding
import com.example.roomrentalapplication.databinding.LayoutItemPersonalTitleBinding
import com.example.roomrentalapplication.databinding.LayoutItemTopPersonalBinding
import com.example.roomrentalapplication.extensions.setSafeOnClickListener
import com.example.roomrentalapplication.ui.base.BaseListAdapter

class PersonalAdapter : BaseListAdapter<PersonalFunctionStatic>() {
    companion object {
        const val CONTENT = 0
        const val TITLE = 1
        const val TOP_PERSON = 2
    }

    internal var onLogoutAction: () -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseItemViewHolder {
        return when (viewType) {
            TOP_PERSON -> {
                TopPersonViewHolder(
                    LayoutItemTopPersonalBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            CONTENT -> {
                ContentViewHolder(
                    ItemPersonalContentFunctionBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> {
                TitleViewHolder(
                    LayoutItemPersonalTitleBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }

    inner class ContentViewHolder(val binding: ItemPersonalContentFunctionBinding) :
        BaseItemViewHolder(binding.root) {

        override fun bind(data: PersonalFunctionStatic) {
            binding.apply {
                val margin13dp = root.context.resources.getDimension(R.dimen.margin_13dp).toInt()
                val margin16dp = root.context.resources.getDimension(R.dimen.margin_16dp).toInt()
                this.data = data
                when (data.name) {
                    PersonalFunctionStatic.PASSWORD.name, PersonalFunctionStatic.REQUEST.name -> {
                        ivDivider.visibility = View.INVISIBLE
                    }
                    PersonalFunctionStatic.LOGOUT.name -> {
                        ivDivider.visibility = View.INVISIBLE
                        imgPersonalTabItemMiddleArrowNext.visibility = View.GONE
                        val marginLayoutParams = ViewGroup.MarginLayoutParams(root.layoutParams)
                        marginLayoutParams.setMargins(
                            margin16dp,
                            margin13dp,
                            margin16dp,
                            margin13dp
                        )
                        root.layoutParams = marginLayoutParams
                    }
                }
                initOnclickItem(data.name, binding)
            }
        }

        private fun initOnclickItem(name: String, binding: ItemPersonalContentFunctionBinding) {
            when (name) {
                PersonalFunctionStatic.LOGOUT.name -> {
                    binding.root.setSafeOnClickListener {
                        onLogoutAction.invoke()
                    }
                }
                else -> {
                    binding.apply {
                        root.setSafeOnClickListener {
                            Toast.makeText(
                                root.context,
                                root.context.getString(data?.stringRes ?: 0),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }

    inner class TitleViewHolder(val binding: LayoutItemPersonalTitleBinding) :
        BaseItemViewHolder(binding.root) {

        override fun bind(data: PersonalFunctionStatic) {
            binding.data = data
        }
    }

    inner class TopPersonViewHolder(val binding: LayoutItemTopPersonalBinding) :
        BaseItemViewHolder(binding.root) {

        override fun bind(data: PersonalFunctionStatic) {
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (getItem(position).isTopPerson) {
            return TOP_PERSON
        }
        if (getItem(position).title == AppConstant.ZERO) {
            return CONTENT
        }
        return TITLE
    }
}
