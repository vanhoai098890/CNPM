package com.example.roomrentalapplication.ui.date_dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.roomrentalapplication.R
import com.example.roomrentalapplication.data.remote.api.model.room.RoomItem
import com.example.roomrentalapplication.databinding.LayoutDateRentDialogBinding
import com.example.roomrentalapplication.extensions.calcDatesWith
import com.example.roomrentalapplication.extensions.onSuccess
import com.example.roomrentalapplication.extensions.setSafeOnClickListener
import com.example.roomrentalapplication.ui.base.BaseDialogFragment
import com.example.roomrentalapplication.ui.date_dialog.adapter.MonthAdapter
import com.example.roomrentalapplication.ui.date_dialog.model.ItemDate
import com.example.roomrentalapplication.ui.date_dialog.model.MonthEnum
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import java.util.*

@SuppressLint("NotifyDataSetChanged")
@AndroidEntryPoint
class DateRentDialog : BaseDialogFragment() {
    private val viewModel: DateRentViewModel by viewModels()
    private lateinit var binding: LayoutDateRentDialogBinding

    companion object {
        private const val FIRST_DATE = 1
        private const val SECOND_DATE = 2
        private const val ROOM_ITEM = "ROOM_ITEM"

        fun newInstance(roomItem: RoomItem): DateRentDialog {
            val args = Bundle().apply {
                putParcelable(ROOM_ITEM, roomItem)
            }
            val fragment = DateRentDialog()
            fragment.arguments = args
            return fragment
        }
    }

    private var mMonth = GregorianCalendar().get(Calendar.MONTH) + 1
    private var mYear = GregorianCalendar().get(Calendar.YEAR)
    private var countBlock = 1
    private val myListDates: MutableList<ItemDate> = mutableListOf()
    private var firstDate: GregorianCalendar? = null
    private var secondDate: GregorianCalendar? = null

    private val adapter: MonthAdapter by lazy {
        MonthAdapter().apply {
            onClick = {
                when (countBlock) {
                    FIRST_DATE -> {
                        firstDate = GregorianCalendar(mYear, mMonth - 1, it.numberDate.toInt())
                        selectFirstDate(firstDate!!)
                        adapter.notifyDataSetChanged()
                        countBlock += 1
                    }
                    SECOND_DATE -> {
                        secondDate = GregorianCalendar(mYear, mMonth - 1, it.numberDate.toInt())
                        selectSecondDate(firstDate, secondDate)
                        adapter.notifyDataSetChanged()
                        countBlock += 1
                        calcPrice()
                    }
                }
            }
        }
    }

    private fun calcPrice() {
        val numberDates = firstDate!!.calcDatesWith(secondDate!!)
        binding.apply {
            tvDateRent.text = getString(
                R.string.v1_date_rent,
                numberDates
            )
            tvNights.text = getString(R.string.v1_night_rent, numberDates)
            viewModel.stateEnablePayButton.value = true
            arguments?.apply {
                getParcelable<RoomItem>(ROOM_ITEM)?.let { roomItem ->
                    viewModel.getPrice(roomItem.priceId ?: 0, firstDate!!, secondDate!!).onSuccess {
                        tvPriceTotal.text = getString(R.string.v1_price_days, it.price.toString())
                    }.launchIn(lifecycleScope)
                }
            }
        }
    }

    override fun setContentDialog(dialog: Dialog) {
        binding = LayoutDateRentDialogBinding.inflate(LayoutInflater.from(context))
        dialog.setContentView(binding.root)
        binding.apply {
            lifecycleOwner = requireParentFragment().viewLifecycleOwner
            data = viewModel
            arguments?.apply {
                getParcelable<RoomItem>(ROOM_ITEM)?.let { roomItem ->
                    tvPrice.text =
                        getString(R.string.v1_price_days, (roomItem.dayPrice ?: 0).toString())
                    layoutCalendar.apply {
                        rvMonthView.layoutManager = GridLayoutManager(requireContext(), 7)
                        rvMonthView.adapter = adapter
                        btnPrevious.setOnClickListener {
                            if (mMonth == 1) {
                                mMonth = 12
                                mYear -= 1
                            } else {
                                mMonth -= 1
                            }
                            initCalendar(mMonth, mYear)
                        }
                        btnNext.setOnClickListener {
                            if (mMonth == 12) {
                                mMonth = 1
                                mYear += 1
                            } else {
                                mMonth += 1
                            }
                            initCalendar(mMonth, mYear)
                        }
                    }
                    initCalendar(mMonth, mYear)
                    ivClose.setSafeOnClickListener {
                        dismiss()
                    }
                    btnClear.setSafeOnClickListener {
                        binding.tvDateRent.text = ""
                        binding.tvNights.text = ""
                        tvPriceTotal.text = ""
                        viewModel.stateEnablePayButton.value = false
                        firstDate = null
                        secondDate = null
                        countBlock = FIRST_DATE
                        initCalendar(mMonth, mYear)
                    }
                }
            }
        }
    }

    override fun initListeners(dialog: Dialog) {
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
            setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
            )
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initCalendar(month: Int, year: Int) {
        var dateOfWeek = 0
        myListDates.clear()
        myListDates.addAll(
            mutableListOf(
                ItemDate("S", isTitle = true),
                ItemDate("M", isTitle = true),
                ItemDate("T", isTitle = true),
                ItemDate("W", isTitle = true),
                ItemDate("T", isTitle = true),
                ItemDate("F", isTitle = true),
                ItemDate("S", isTitle = true),
            )
        )
        if (myListDates.size <= 7) {
            binding.layoutCalendar.tvMonthYear.text = getString(
                R.string.calendar_title,
                getString(MonthEnum.values()[mMonth - 1].monthRes),
                mYear
            )
            val myCal = GregorianCalendar(year, month - 1, 1)
            val daysInMonth = myCal.getActualMaximum(Calendar.DAY_OF_MONTH)
            dateOfWeek = myCal.get(Calendar.DAY_OF_WEEK)
            for (i in 1..42) {
                if (i >= dateOfWeek && i < dateOfWeek + daysInMonth
                ) {
                    myListDates.add(
                        ItemDate(
                            "${i - dateOfWeek + 1}",
                            false
                        )
                    )
                } else {
                    myListDates.add(ItemDate("0", false))
                }
            }
            adapter.submitList(myListDates)
        }
        when (countBlock) {
            FIRST_DATE -> {
                arguments?.apply {
                    getParcelable<RoomItem>(ROOM_ITEM).let { roomItem ->
                        viewModel.getDateStatusByRoomId(roomItem?.roomId ?: 0, mMonth, mYear)
                            .onSuccess {
                                it.data.forEachIndexed { index, dateStatus ->
                                    myListDates[6 + dateOfWeek + index].isEnable =
                                        !dateStatus.status
                                }
                                adapter.notifyDataSetChanged()
                            }.launchIn(lifecycleScope)
                    }
                }
            }
        }
        selectFirstDate(firstDate)
        selectSecondDate(firstDate, secondDate)
        adapter.notifyDataSetChanged()
    }

    private fun selectSecondDate(firstDate: GregorianCalendar?, secondDate: GregorianCalendar?) {
        secondDate?.apply {
            firstDate?.apply {
                for (i in 7..48) {
                    if (myListDates[i].numberDate != "") {
                        if (mYear > secondDate.get(Calendar.YEAR)) {
                            myListDates[i].isEnable = false
                        } else if (mYear == secondDate.get(Calendar.YEAR)) {
                            handleSecondDateWhenEqualYear(firstDate, secondDate, i)
                        }
                    }
                }
            }
        }
    }

    private fun handleSecondDateWhenEqualYear(
        firstDate: GregorianCalendar,
        secondDate: GregorianCalendar,
        i: Int
    ) {
        if (mMonth - 1 > secondDate.get(Calendar.MONTH)) {
            myListDates[i].isEnable = false
        } else if (mMonth - 1 == secondDate.get(Calendar.MONTH)) {
            if (myListDates[i].numberDate.toInt() > secondDate.get(Calendar.DATE)) {
                myListDates[i].isEnable = false
            } else if (myListDates[i].numberDate.toInt() <= secondDate.get(Calendar.DATE)
            ) {
                if (
                    mYear > firstDate.get(Calendar.YEAR)
                    || mMonth - 1 > firstDate.get(Calendar.MONTH)
                    || myListDates[i].numberDate.toInt() >= firstDate.get(Calendar.DATE)
                ) {
                    if (myListDates[i].isEnable) {
                        myListDates[i].isSelected = true
                    }
                } else {
                    myListDates[i].isEnable = false
                }
            }
        } else if (mMonth - 1 < secondDate.get(Calendar.MONTH) && mMonth - 1 >= firstDate.get(
                Calendar.MONTH
            )
        ) {
            if (myListDates[i].isEnable) {
                myListDates[i].isSelected = true
            }
        } else {
            myListDates[i].isEnable = false
        }
    }

    private fun selectFirstDate(firstDate: GregorianCalendar?) {
        firstDate?.apply {
            for (i in 7..48) {
                if (myListDates[i].numberDate != "") {
                    if (mYear < firstDate.get(Calendar.YEAR)) {
                        myListDates[i].isEnable = false
                    } else if (mYear == firstDate.get(Calendar.YEAR)) {
                        if (mMonth - 1 < firstDate.get(Calendar.MONTH)) {
                            myListDates[i].isEnable = false
                        } else if (mMonth - 1 == firstDate.get(Calendar.MONTH)) {
                            if (myListDates[i].numberDate.toInt() < firstDate.get(Calendar.DATE)) {
                                myListDates[i].isEnable = false
                            } else if (myListDates[i].numberDate.toInt() == firstDate.get(Calendar.DATE)) {
                                myListDates[i].isSelected = true
                            }
                        }
                    }
                }
            }
        }
    }
}
