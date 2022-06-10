package com.example.roomrentalapplication.ui.request_info_dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.roomrentalapplication.R
import com.example.roomrentalapplication.data.remote.api.model.request_info.RentRequest
import com.example.roomrentalapplication.data.remote.api.model.room.RoomItem
import com.example.roomrentalapplication.databinding.LayoutConfirmInformationBinding
import com.example.roomrentalapplication.extensions.getFormatString
import com.example.roomrentalapplication.extensions.setSafeOnClickListener
import com.example.roomrentalapplication.ui.base.BaseDialogFragment
import com.example.roomrentalapplication.ui.base.BaseFragment
import com.example.roomrentalapplication.ui.failed_dialog.FailedDialog
import com.example.roomrentalapplication.ui.success_dialog.SuccessDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class RequestInfoDialog : BaseDialogFragment() {
    private lateinit var binding: LayoutConfirmInformationBinding
    private val viewModel: RequestInfoViewModel by viewModels()

    companion object {

        const val ROOM_ITEM = "ROOM_ITEM"
        const val DAY_RENT = "DAY_RENT"
        const val AMOUNT = "AMOUNT"
        const val FIRST_DATE = "FIRST_DATE"
        const val SECOND_DATE = "SECOND_DATE"

        fun newInstance(
            roomItem: RoomItem,
            daysRent: Int,
            amount: Double,
            firstDate: String,
            secondDate: String
        ): RequestInfoDialog {
            val args = Bundle().apply {
                putParcelable(
                    ROOM_ITEM, roomItem
                )
                putInt(DAY_RENT, daysRent)
                putDouble(AMOUNT, amount)
                putString(FIRST_DATE, firstDate)
                putString(SECOND_DATE, secondDate)
            }
            val fragment = RequestInfoDialog()
            fragment.arguments = args
            return fragment
        }
    }

    @SuppressLint("SetTextI18n")
    override fun setContentDialog(dialog: Dialog) {
        binding = LayoutConfirmInformationBinding.inflate(LayoutInflater.from(context))
        binding.lifecycleOwner = requireParentFragment().viewLifecycleOwner
        dialog.setContentView(binding.root)
        binding.apply {
            ivClose.setSafeOnClickListener {
                dismiss()
            }
            arguments?.apply {
                getInt(DAY_RENT).apply {
                    tvDaysRent.text = "$this"
                }
                getDouble(AMOUNT).apply {
                    tvAmount.text = getString(R.string.v1_price_days, this)
                    tvDeposit.text = getString(
                        R.string.v1_price_days,
                        (this * 0.35)
                    ) + getString(R.string.v1_thirty_five_percent)
                    viewModel.total.value = this
                    viewModel.deposit.value = this * 0.35
                }
                getString(FIRST_DATE)?.apply {
                    tvCheckIn.text = this
                    viewModel.firstDate.value = this
                }
                getString(SECOND_DATE)?.apply {
                    tvCheckOut.text = this
                    viewModel.secondDate.value = this
                }
                getParcelable<RoomItem>(ROOM_ITEM)?.apply {
                    btnRequestInfo.setSafeOnClickListener {
                        viewModel.confirmRentRoom(
                            RentRequest(
                                this.roomId,
                                viewModel.customer.value?.customerId ?: 0,
                                Date().getFormatString(),
                                viewModel.firstDate.value,
                                viewModel.secondDate.value,
                                total = viewModel.total.value,
                                deposit = viewModel.deposit.value
                            )
                        )
                    }
                }
            }
        }
    }

    override fun initListeners(dialog: Dialog) {
        binding.data = viewModel.getCustomerInfo()
        lifecycleScope.launchWhenStarted {
            launch {
                viewModel.loadingState().collect {
                    (requireParentFragment() as? BaseFragment)?.handleShowLoadingDialog(it)
                }
            }
            launch {
                viewModel.stateOnSuccessRentAction.collect {
                    if (it) {
                        SuccessDialog().show(parentFragmentManager, null)
                    }
                }
            }
            launch {
                viewModel.stateOnErrorAction.collect {
                    if (it) {
                        FailedDialog().show(parentFragmentManager, null)
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
            setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
            )
        }
    }
}
