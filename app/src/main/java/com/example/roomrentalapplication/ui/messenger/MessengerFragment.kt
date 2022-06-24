package com.example.roomrentalapplication.ui.messenger

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.roomrentalapplication.databinding.MessengerFragmentBinding
import com.example.roomrentalapplication.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MessengerFragment : BaseFragment() {

    companion object {
        fun newInstance() = MessengerFragment()
    }

    private val viewModel: MessengerViewModel by viewModels()
    private lateinit var binding: MessengerFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MessengerFragmentBinding.inflate(inflater, container, false)
        binding.apply {

        }
        return binding.root
    }

}
