package com.example.roomrentalapplication.ui.base

import android.os.SystemClock
import androidx.fragment.app.Fragment

abstract class BaseFragment : FragmentController() {
    var timeNow = 0L
    internal fun addNoNavigationFragment(
        fragment: Fragment,
        isEnableAnim: Boolean = true, tagNameBackStack: String? = null
    ) {
        (activity as? BaseActivity)?.addNoNavigationFragment(
            fragment,
            isEnableAnim,
            tagNameBackStack
        )
    }

    internal fun replaceNoNavigationFragment(
        fragment: Fragment, isAddBackStack: Boolean,
        isEnableAnim: Boolean = true, tagNameBackStack: String? = null
    ) {
        (activity as? BaseActivity)?.replaceNoNavigationFragment(
            fragment,
            isAddBackStack,
            isEnableAnim,
            tagNameBackStack
        )
    }

    internal fun showLoading() {
        (activity as? BaseActivity)?.showLoading()
    }

    internal fun hideLoading() {
        (activity as? BaseActivity)?.hideLoading()
    }

    internal fun handleShowLoadingDialog(isStateShow: Boolean) {
        if (isStateShow) {
            showLoading()
        } else {
            hideLoading()
        }
    }

    internal fun showBottomSheet(bottomSheet: BaseBottomSheetDialogFragment) {
        bottomSheet.show(parentFragmentManager, bottomSheet.javaClass.name)
    }

    internal fun callSafeAction(delayInterval: Long = 500, action: () -> Unit) {
        SystemClock.elapsedRealtime().run {
            if (this - timeNow < delayInterval) {
                return
            }
            timeNow = this
            action.invoke()
        }
    }

    internal fun recreateMainScreen() {
        (activity as? BaseActivity)?.recreateMainScreen()
    }
}
