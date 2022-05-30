package com.example.roomrentalapplication.ui.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.roomrentalapplication.R
import com.example.roomrentalapplication.databinding.FavouriteFragmentBinding
import com.example.roomrentalapplication.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouriteFragment : BaseFragment() {

    companion object {
        fun newInstance() = FavouriteFragment()
    }

    private val viewModel: FavouriteViewModel by viewModels()
    private lateinit var binding: FavouriteFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FavouriteFragmentBinding.inflate(inflater,container,false)
        binding.apply {

        }
        return binding.root
    }
}
