package com.example.roomrentalapplication.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.roomrentalapplication.R
import com.example.roomrentalapplication.data.AppConstant
import com.example.roomrentalapplication.extensions.replaceFragment
import com.example.roomrentalapplication.ui.base.BaseActivity
import com.example.roomrentalapplication.ui.base.BaseFragment
import com.example.roomrentalapplication.ui.splash.sign_in.SignInFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setLevel(AppConstant.LEVEL_CONTAINER)
        view.postDelayed(
            {
                (requireActivity() as? BaseActivity)?.replaceFragment(
                    SignInFragment().apply {
                        setLevel(AppConstant.LEVEL_TAB)
                    },
                    isAddBackStack = false
                )
            },
            1000L
        )
        super.onViewCreated(view, savedInstanceState)
    }
}