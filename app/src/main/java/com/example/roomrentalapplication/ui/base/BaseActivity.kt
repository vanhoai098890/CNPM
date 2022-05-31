package com.example.roomrentalapplication.ui.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.roomrentalapplication.data.AppConstant
import com.example.roomrentalapplication.databinding.ActivityMainBinding
import com.example.roomrentalapplication.extensions.addFragment
import com.example.roomrentalapplication.extensions.replaceFragment
import com.example.roomrentalapplication.ui.MainActivity
import com.example.roomrentalapplication.ui.common.LoadingDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class BaseActivity : AppCompatActivity() {


    private var navigationContainer: BaseFragment? = null
    private var noNavigationContainer: BaseFragment? = null
    private val loadingDialog by lazy { LoadingDialog() }
    protected var binding: ActivityMainBinding? = null

    abstract fun navigationContainer(): BaseFragment?

    abstract fun noNavigationContainer(): BaseFragment?

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        super.onCreate(savedInstanceState)
        initBase()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        navigationContainer?.let {
            replaceFragment(it, false, isEnableAnim = false)
        }
        noNavigationContainer?.let {
            addFragment(it, false)
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        hideSystemUI(this)
        super.onWindowFocusChanged(hasFocus)
    }

    override fun onResume() {
        hideSystemUI(this)
        super.onResume()
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        super.dispatchTouchEvent(ev)
        ev?.let {
            if (ev.action == MotionEvent.ACTION_DOWN) {
                val viewTemp: View? = currentFocus
                if (viewTemp is EditText) {
                    val outRect = Rect()
                    viewTemp.getGlobalVisibleRect(outRect)
                    if (!outRect.contains(ev.rawX.toInt(), ev.rawY.toInt())) {
                        viewTemp.clearFocus()
                        hideKeyboard(viewTemp)
                    }
                }
            }
        }
        return true
    }

    /**
     * Region method Internal, public
     */
    internal fun addNoNavigationFragment(
        fragment: Fragment,
        isEnableAnim: Boolean = true, tagNameBackStack: String? = null
    ) {
        noNavigationContainer?.addInContainer(fragment, isEnableAnim, tagNameBackStack)
    }

    internal fun replaceNoNavigationFragment(
        fragment: Fragment, isAddBackStack: Boolean,
        isEnableAnim: Boolean = true, tagNameBackStack: String? = null
    ) {
        noNavigationContainer?.replaceInContainer(
            fragment,
            isAddBackStack,
            isEnableAnim,
            tagNameBackStack
        )
    }

    internal fun showLoading() {
        loadingDialog.show(supportFragmentManager, loadingDialog.javaClass.name)
    }

    internal fun hideLoading() {
        loadingDialog.dismiss()
    }

    internal fun clearNoNavigationContainerChildBackStack() {
        noNavigationContainer?.childFragmentManager?.popBackStack(
            null,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
    }

    /**
     * Region Private
     */
    private fun initBase() {
        navigationContainer = navigationContainer()
        noNavigationContainer = noNavigationContainer()?.apply {
            setLevel(AppConstant.LEVEL_CONTAINER)
        }
    }

    private fun hideKeyboard(v: View?) {
        v?.let {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

    internal fun recreateMainScreen() {
        startActivity(Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        })
    }

    open fun hideSystemUI(activity: Activity) {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        val decorView = activity.window.decorView
        decorView.systemUiVisibility =
            (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY // Set the content to appear under the system bars so that the
                    // content doesn't resize when the system bars hide and show.
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN // Hide the nav bar and status bar
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }

    override fun onStart() {
        super.onStart()
        hideSystemUI(this)
    }
}
