package com.example.roomrentalapplication.ui.success_dialog

import android.app.Dialog
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.roomrentalapplication.databinding.LayoutSuccessDialogBinding
import com.example.roomrentalapplication.ui.base.BaseActivity
import com.example.roomrentalapplication.ui.base.BaseDialogFragment
import com.example.roomrentalapplication.utils.bindingadapter.setSafeOnClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SuccessDialog : BaseDialogFragment() {

    private lateinit var binding: LayoutSuccessDialogBinding

    override fun setContentDialog(dialog: Dialog) {
        dialog.apply {
            binding = LayoutSuccessDialogBinding.inflate(LayoutInflater.from(context))
            setContentView(binding.root)
            binding.apply {
                btnSuccess.setSafeOnClickListener {
                    (requireActivity() as? BaseActivity)?.recreateMainScreen()
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
}
