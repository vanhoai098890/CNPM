package com.example.roomrentalapplication.ui.base

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
abstract class BaseAdapterViewPager2(
    fragment: Fragment,
    private val levelParent: Int,
    private val fragmentPagers: List<BaseFragment>
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = fragmentPagers.size

    override fun createFragment(position: Int): Fragment = fragmentPagers[position].apply {
        setLevel(levelParent + 1)
    }
}
