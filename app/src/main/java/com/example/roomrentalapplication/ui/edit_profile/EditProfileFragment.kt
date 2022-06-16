package com.example.roomrentalapplication.ui.edit_profile

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.roomrentalapplication.R
import com.example.roomrentalapplication.databinding.LayoutEditProfileFragmentBinding
import com.example.roomrentalapplication.extensions.setSafeOnClickListener
import com.example.roomrentalapplication.ui.base.BaseFragment
import com.example.roomrentalapplication.ui.nationality.NationalityBottomSheet
import com.example.roomrentalapplication.ui.nationality.model.NationalityEnum
import com.example.roomrentalapplication.utils.bindingadapter.bindTextNationality
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class EditProfileFragment : BaseFragment() {

    private lateinit var binding: LayoutEditProfileFragmentBinding
    private val myViewModel: EditProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LayoutEditProfileFragmentBinding.inflate(inflater, container, false)
        binding.apply {
            viewModel = myViewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initEvents()
    }

    private fun initEvents() {
        lifecycleScope.launchWhenStarted {
            launch {
                myViewModel.loadingState().collect {
                    handleShowLoadingDialog(it)
                }
            }
            launch {
                myViewModel.stateUpdateSuccess.collect {
                    if (it) {
                        Toast.makeText(context, "Update success!!", Toast.LENGTH_SHORT).show()
                        myViewModel.stateUpdateSuccess.value = false
                    }
                }
            }
            launch {
                myViewModel.stateUpdateError.collect {
                    if (it) {
                        Toast.makeText(context, "Something wrong!!", Toast.LENGTH_SHORT).show()
                        myViewModel.stateUpdateError.value = false
                    }
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initView() {
        binding.apply {
            toolbarId.tvCenter.text = getString(R.string.v1_edit_profile)
            toolbarId.backButton.setSafeOnClickListener {
                handleBackPressed()
            }
            btnUpdateAvatar.setSafeOnClickListener {
                Toast.makeText(requireContext(), "Not implemented yet!!", Toast.LENGTH_SHORT).show()
            }
            layoutNational.setSafeOnClickListener {
                var nationalityId = -1
                myViewModel.stateCustomerProperty.value?.apply {
                    NationalityEnum.values().forEachIndexed { index, nationalityEnum ->
                        if (nationalityEnum.name == myViewModel.stateCustomerProperty.value?.nationality) {
                            nationalityId = index
                            return@forEachIndexed
                        }
                    }
                }
                showBottomSheet(NationalityBottomSheet.newInstance(nationalityId).apply {
                    onFirstItemClick = {
                        myViewModel.stateCustomerProperty.value?.nationality = it.name
                        layoutNational.bindTextNationality(getString(it.resString))
                    }
                })
            }
            val myCalendar = Calendar.getInstance()
            edtBirthDay.setSafeOnClickListener {
                var tempYear = myCalendar.get(Calendar.YEAR)
                var tempMonth = myCalendar.get(Calendar.MONTH)
                var tempDate = myCalendar.get(Calendar.DAY_OF_MONTH)
                myViewModel.stateCustomerProperty.value?.apply {
                    birthday?.let { birthday ->
                        if (birthday.isNotBlank()) {
                            val tempTime = birthday.split('-')
                            tempYear = tempTime[0].toInt()
                            tempMonth = tempTime[1].toInt()
                            tempDate = tempTime[2].toInt()
                        }
                    }
                }
                DatePickerDialog(
                    requireContext(),
                    { _, year, month, dayOfMonth ->
                        edtBirthDay.setText(
                            String.format(
                                "%04d-%02d-%02d",
                                year,
                                month,
                                dayOfMonth
                            )
                        )
                        myViewModel.stateCustomerProperty.value?.birthday =
                            String.format(
                                "%04d-%02d-%02d",
                                year,
                                month,
                                dayOfMonth
                            )
                    },
                    tempYear,
                    tempMonth,
                    tempDate
                ).show()
            }
            btnSubmit.setSafeOnClickListener {
                myViewModel.submitPersonalInfo()
            }
        }
    }
}
