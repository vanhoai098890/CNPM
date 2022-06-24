package com.example.roomrentalapplication.ui.request.adapter

import androidx.fragment.app.Fragment
import com.example.roomrentalapplication.ui.base.BaseAdapterViewPager2
import com.example.roomrentalapplication.ui.base.BaseFragment

class RequestAdapter(
    fragment: Fragment,
    levelParent: Int,
    fragmentPagers: List<BaseFragment>
) : BaseAdapterViewPager2(fragment, levelParent, fragmentPagers)
