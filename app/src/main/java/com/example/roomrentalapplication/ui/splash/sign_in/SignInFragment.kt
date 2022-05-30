package com.example.roomrentalapplication.ui.splash.sign_in

import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.roomrentalapplication.ui.MainActivity
import com.example.roomrentalapplication.R
import com.example.roomrentalapplication.data.AppConstant
import com.example.roomrentalapplication.databinding.FragmentSignInBinding
import com.example.roomrentalapplication.extensions.replaceFragment
import com.example.roomrentalapplication.extensions.setSafeOnClickListener
import com.example.roomrentalapplication.ui.base.BaseActivity
import com.example.roomrentalapplication.ui.base.BaseFragment
import com.example.roomrentalapplication.ui.splash.sign_up.SignUpFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignInFragment : BaseFragment() {
    private var binding: FragmentSignInBinding? = null
    private val viewModel: SignInViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_in, container, false)
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            data = viewModel
        }
        initData()
        initEvents()
        return binding?.root
    }

    private fun initEvents() {
        lifecycleScope.launchWhenStarted {
            launch {
                viewModel.loadingState().collect {
                    handleShowLoadingDialog(it)
                }
            }
            launch {
                viewModel.navigateToHomeStatus.collect {
                    if (it) {
                        handleRemember()
                        viewModel.navigateToHomeStatus.value = false
                    }
                }
            }
        }
    }

    private fun handleRemember() {
        binding?.apply {
            if (checkboxRemember.isChecked) {
                saveInfo()
            } else {
                clearInfo()
            }
        }
    }

    private fun clearInfo() {
        if (!viewModel.clearRemember()) {
            viewModel.isShowError.value = SignInViewModel.ERROR_DATABASE
        } else {
            navToHomeActivity()
        }
    }

    private fun saveInfo() {
        if (!viewModel.saveUserInfo()) {
            viewModel.isShowError.value = SignInViewModel.ERROR_DATABASE
        } else {
            navToHomeActivity()
        }
    }

    private fun navToHomeActivity() {
        startActivity(Intent(requireActivity(), MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }

    private fun togglePasswordVisibility() {
        binding?.apply {
            if (viewModel.isPasswordVisible) {
                edtPassword.transformationMethod = PasswordTransformationMethod()
                ibTogglePassword.setImageResource(R.drawable.ic_password_toggle_invisible)
                edtPassword.setSelection(edtPassword.length())
            } else {
                edtPassword.transformationMethod = null
                ibTogglePassword.setImageResource(R.drawable.ic_password_toggle_visible)
                edtPassword.setSelection(edtPassword.length())
            }
            viewModel.isPasswordVisible = !viewModel.isPasswordVisible
        }
    }

    private fun clearInput(editText: EditText) {
        editText.setText(AppConstant.EMPTY)
        editText.requestFocus()
    }

    private fun initData() {
        binding?.apply {
            tvSignUp.setSafeOnClickListener {
                (requireActivity() as? BaseActivity)?.replaceFragment(SignUpFragment().apply {
                    setLevel(AppConstant.LEVEL_TAB)
                }, true)
            }
            tvForgotPass.setSafeOnClickListener {
                Toast.makeText(context, "Not implemented yet!!", Toast.LENGTH_SHORT).show()
            }
            ibTogglePassword.setOnClickListener {
                togglePasswordVisibility()
            }
            ibClearPassword.setOnClickListener {
                clearInput(edtPassword)
            }
            ibClearUsername.setOnClickListener {
                clearInput(edtUsername)
            }
        }
    }
}
