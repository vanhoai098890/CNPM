package com.example.roomrentalapplication.ui.splash.onboard_welcome

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.roomrentalapplication.R
import com.example.roomrentalapplication.databinding.FragmentOnBoardBinding
import com.example.roomrentalapplication.extensions.setSafeOnClickListener
import com.example.roomrentalapplication.ui.base.BaseFragment
import com.example.roomrentalapplication.ui.splash.SplashActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardFragment : BaseFragment() {
    private var binding: FragmentOnBoardBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_on_board, container, false)
        initData()
        return binding?.root
    }

    private fun initData() {
        binding?.apply {
            btnLetGo.setSafeOnClickListener {
                startActivity(Intent(context, SplashActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                })
            }
        }
    }

    override fun handleBackPressed(tagNameBackStack: String?) {}
}