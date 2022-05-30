package com.example.roomrentalapplication.ui.base

import android.content.DialogInterface
import android.view.View
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheetDialogFragment : BottomSheetDialogFragment() {

    companion object {
        private const val ZERO = 0

        @Volatile
        private var isShowing = false
    }

    internal var heightOfDialog: Int = ZERO

    override fun onStart() {
        super.onStart()
        fixHeightForDialog()
    }

    override fun show(manager: FragmentManager, tag: String?) {
        if (!isShowing && !isAdded) {
            isShowing = true
            super.show(manager, tag)
        }
    }

    override fun dismiss() {
        if (isShowing && isAdded) {
            super.dismiss()
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        if (isShowing) {
            isShowing = false
            super.onDismiss(dialog)
        }
    }

    private fun fixHeightForDialog() {
        if (heightOfDialog != ZERO) {
            dialog?.also {
                val bottomSheet = it.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
                bottomSheet.layoutParams.height = heightOfDialog
                val behavior = BottomSheetBehavior.from(bottomSheet)
                behavior.peekHeight = heightOfDialog
                view?.requestLayout()
            }
        }
    }
}
