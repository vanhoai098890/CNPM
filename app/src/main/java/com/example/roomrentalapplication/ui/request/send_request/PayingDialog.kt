package com.example.roomrentalapplication.ui.request.send_request

import android.app.Activity.RESULT_OK
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.roomrentalapplication.data.AppConstant
import com.example.roomrentalapplication.data.remote.api.model.received_request.ReceivedRequestItem
import com.example.roomrentalapplication.databinding.DialogPayingBinding
import com.example.roomrentalapplication.extensions.setSafeOnClickListener
import com.example.roomrentalapplication.ui.base.BaseDialogFragment
import com.example.roomrentalapplication.utils.LogUtils
import com.paypal.android.sdk.payments.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.math.BigDecimal


@AndroidEntryPoint
class PayingDialog private constructor() : BaseDialogFragment() {

    private lateinit var binding: DialogPayingBinding
    private val viewModel: PayingViewModel by viewModels()
    private val config = PayPalConfiguration()
        .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
        .clientId(AppConstant.PAYPAL_CLIENT_ID)
        .merchantName("Paypal Login")
        .merchantPrivacyPolicyUri(Uri.parse("https://www.example.com/privacy"))
        .merchantUserAgreementUri(Uri.parse("https://www.example.com/legal"))
    private val activityResultLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                result.data?.apply {
                    val confirmation =
                        getParcelableExtra<PaymentConfirmation>(PaymentActivity.EXTRA_RESULT_CONFIRMATION)

                    confirmation?.apply {
                        val paymentDetails = confirmation.toJSONObject().toString(4)
                        LogUtils.d(" Paying Dialog $paymentDetails")
                    }
                }
            }
        }

    override fun setContentDialog(dialog: Dialog) {
        binding = DialogPayingBinding.inflate(LayoutInflater.from(context))
        binding.apply {
            lifecycleOwner = requireParentFragment().viewLifecycleOwner
            data = viewModel
        }
        val intent = Intent(requireContext(), PayPalService::class.java).apply {
            putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config)
        }
        requireActivity().startService(intent)
        initViews()
        dialog.setContentView(binding.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        requireActivity().stopService(Intent(requireContext(), PayPalService::class.java))
    }

    private fun initViews() {
        binding.apply {
            ivClose.setSafeOnClickListener {
                dismiss()
            }
            ivMess.setSafeOnClickListener {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.fromParts(
                            "sms",
                            viewModel.stateCustomerProperty.value.phoneNumber,
                            null
                        )
                    )
                )
            }
            ivPhone.setSafeOnClickListener {
                startActivity(
                    Intent(
                        Intent.ACTION_DIAL,
                        Uri.parse("tel: ${viewModel.stateCustomerProperty.value.phoneNumber}")
                    )
                )
            }
            btnCancel.setSafeOnClickListener {
                // Cancel
                viewModel.updateStatusReservation(4)
            }
            payPalButton.setSafeOnClickListener {
                val payment = PayPalPayment(
                    BigDecimal(
                        viewModel.stateReceivedRequestItem.value?.total?.toString() ?: "0.0"
                    ),
                    "USD",
                    "Payment for rent",
                    PayPalPayment.PAYMENT_INTENT_SALE
                )
                val intent = Intent(requireContext(), PaymentActivity::class.java)
                intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config)
                intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment)
                activityResultLauncher.launch(intent)
            }
        }
    }

    override fun initListeners(dialog: Dialog) {
        binding.apply {
            arguments?.apply {
                getParcelable<ReceivedRequestItem>(RECEIVED_ITEM)?.apply {
                    viewModel.stateReceivedRequestItem.value = this
                    viewModel.getCustomerById(customerId)
                    when (status) {
                        AppConstant.PAYING -> {
                            payPalButton.isEnabled = true
                        }
                        AppConstant.CANCELED, AppConstant.APPROVED -> {
                            payPalButton.isEnabled = false
                            btnCancel.isEnabled = false
                        }
                        else -> {
                            payPalButton.isEnabled = false
                            btnCancel.isEnabled = true
                        }
                    }
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            launch {
                viewModel.stateSuccess.collect {
                    if (it) {
                        Toast.makeText(requireContext(), "Action succeed!!!", Toast.LENGTH_SHORT)
                            .show()
                        viewModel.stateSuccess.value = false
                    }
                }
            }
            launch {
                viewModel.stateError.collect {
                    if (it) {
                        Toast.makeText(requireContext(), "Action failed!!!", Toast.LENGTH_SHORT)
                            .show()
                        viewModel.stateError.value = false
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

    companion object {
        private const val RECEIVED_ITEM = "RECEIVED_ITEM"
        fun newInstance(receivedRequestItem: ReceivedRequestItem): PayingDialog {
            val args = Bundle().apply {
                putParcelable(RECEIVED_ITEM, receivedRequestItem)
            }
            val fragment = PayingDialog()
            fragment.arguments = args
            return fragment
        }
    }
}
