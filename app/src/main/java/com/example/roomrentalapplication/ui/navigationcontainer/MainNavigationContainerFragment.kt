package com.example.roomrentalapplication.ui.navigationcontainer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.roomrentalapplication.R
import com.example.roomrentalapplication.databinding.FragmentNavigationContainerBinding
import com.example.roomrentalapplication.ui.add_property.AddPropertyFragment
import com.example.roomrentalapplication.ui.add_room.AddRoomFragment
import com.example.roomrentalapplication.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainNavigationContainerFragment : BaseFragment() {
    private var binding: FragmentNavigationContainerBinding? = null
    private val viewModel: MainNavigationContainerViewModel by viewModels()
    private val fragmentPagers: MutableList<PagerContainerFragment> by lazy {
        mutableListOf()
    }
    private var isOpen = false
    private var fabOpen: Animation? = null
    private var fabClose: Animation? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initFragments()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentNavigationContainerBinding.inflate(inflater, container, false)
        binding?.run {
            data = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        initViews()
        return binding?.root
    }

    private fun initViews() {
        fabOpen = AnimationUtils.loadAnimation(requireContext(), R.anim.fab_open)
        fabClose = AnimationUtils.loadAnimation(requireContext(), R.anim.fab_close)
        binding?.run {
            vpContainer.let {
                it.adapter =
                    MainNavigationVpAdapter(
                        this@MainNavigationContainerFragment,
                        level,
                        fragmentPagers
                    )
                it.isUserInputEnabled = false
                it.offscreenPageLimit = 1
            }
            bottomNavigation.let {
                it.setOnItemSelectedListener { menuItem ->
                    vpContainer.currentItem = when (menuItem.order + 1) {
                        MainNavigationItem.MESSENGER.ordinal, MainNavigationItem.PERSONAL.ordinal -> {
                            menuItem.order - 1
                        }
                        else -> {
                            menuItem.order
                        }
                    }
                    return@setOnItemSelectedListener true
                }
            }
            btnAdd.setOnClickListener {
                animateFab()
            }
            btnRoom.setOnClickListener {
                animateFab()
                root.post {
                    addNoNavigationFragment(AddRoomFragment())
                }
            }
            btnProperty.setOnClickListener {
                animateFab()
                root.post {
                    addNoNavigationFragment(AddPropertyFragment())
                }
            }
        }
        lifecycleScope.launchWhenStarted {
        }
    }

    private fun initFragments() {
        MainNavigationItem.values().forEachIndexed { index, _ ->
            if (index == MainNavigationItem.TEMP.ordinal) {
                return@forEachIndexed
            }
            fragmentPagers.add(PagerContainerFragment.newInstance(index).apply {
                onCallApiComplete = {
                }
            })
        }
    }

    private fun animateFab() {
        binding?.apply {
            if (isOpen) {
                btnProperty.startAnimation(fabClose)
                btnRoom.startAnimation(fabClose)
                btnProperty.isClickable = false
                btnRoom.isClickable = false
                btnAdd.setImageResource(R.drawable.ic_baseline_add_24)
                isOpen = false
            } else {
                btnProperty.startAnimation(fabOpen)
                btnRoom.startAnimation(fabOpen)
                btnProperty.isClickable = true
                btnRoom.isClickable = true
                btnAdd.setImageResource(R.drawable.ic_baseline_close_24)
                isOpen = true
            }
        }
    }
}
