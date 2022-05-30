package com.example.roomrentalapplication.ui.messenger

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.roomrentalapplication.R
import com.example.roomrentalapplication.databinding.FavouriteFragmentBinding
import com.example.roomrentalapplication.databinding.MessengerFragmentBinding
import com.example.roomrentalapplication.ui.base.BaseFragment
import com.example.roomrentalapplication.ui.favourite.FavouriteViewModel
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
        binding = MessengerFragmentBinding.inflate(inflater,container,false)
        binding.apply {

        }
        return binding.root
    }

}
