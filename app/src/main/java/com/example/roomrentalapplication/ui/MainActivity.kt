package com.example.roomrentalapplication.ui

import android.os.Bundle
import com.example.roomrentalapplication.R
import com.example.roomrentalapplication.ui.base.BaseActivity
import com.example.roomrentalapplication.ui.base.BaseFragment
import com.example.roomrentalapplication.ui.navigationcontainer.MainNavigationContainerFragment

class MainActivity : BaseActivity() {
    override fun navigationContainer(): BaseFragment =
        MainNavigationContainerFragment()

    override fun noNavigationContainer(): BaseFragment =
        MainNoNavigationContainerFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
