package com.example.roomrentalapplication.ui.splash

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import com.example.roomrentalapplication.ui.base.BaseActivity
import com.example.roomrentalapplication.ui.base.BaseFragment
import com.example.roomrentalapplication.utils.LogUtils
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : BaseActivity() {

    override fun navigationContainer(): BaseFragment = SplashFragment()

    override fun noNavigationContainer(): BaseFragment? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtils.d("onDestroy")
    }
}
