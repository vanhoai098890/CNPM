package com.example.roomrentalapplication.ui.personal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomrentalapplication.R
import com.example.roomrentalapplication.databinding.FragmentPersonalBinding
import com.example.roomrentalapplication.ui.base.BaseFragment
import com.example.roomrentalapplication.ui.personal.adapter.LogoutDialog
import com.example.roomrentalapplication.ui.personal.adapter.PersonalAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PersonalFragment() : BaseFragment() {

    private lateinit var binding: FragmentPersonalBinding
    private val viewModel: PersonalViewModel by viewModels()
    private val adapter by lazy {
        PersonalAdapter().apply {
            onLogoutAction = {
                logoutDialog.show(parentFragmentManager, null)
            }
            customerInfo = viewModel.getCustomerInfo()
        }
    }
    private val logoutDialog: LogoutDialog by lazy {
        LogoutDialog()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPersonalBinding.inflate(inflater, container, false)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initEvents()
    }

    private fun initData() {
        binding.apply {
            toolbarId.apply {
                tvCenter.text = context?.getString(R.string.v1_account)
                backButton.visibility = View.INVISIBLE
            }
            rvPersonal.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                adapter = this@PersonalFragment.adapter
            }
        }

    }

    private fun initEvents() {
        lifecycleScope.launchWhenStarted {
            launch {
                viewModel.listPersonal.collect {
                    adapter.submitList(it.toList())
                }
            }
        }
    }
}
