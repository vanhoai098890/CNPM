package com.example.roomrentalapplication.ui.failed_dialog

import android.app.Dialog
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.roomrentalapplication.databinding.DialogLayoutFailedBinding
import com.example.roomrentalapplication.ui.base.BaseActivity
import com.example.roomrentalapplication.ui.base.BaseDialogFragment
import com.example.roomrentalapplication.utils.bindingadapter.setSafeOnClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FailedDialog : BaseDialogFragment() {

    private lateinit var binding: DialogLayoutFailedBinding

    override fun setContentDialog(dialog: Dialog) {
        dialog.apply {
            binding = DialogLayoutFailedBinding.inflate(LayoutInflater.from(context))
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
