package com.example.roomrentalapplication.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.roomrentalapplication.databinding.LayoutImageFragmentBinding
import com.example.roomrentalapplication.ui.base.BaseFragment
import com.example.roomrentalapplication.utils.bindingadapter.loadImageUri
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImageFragment : BaseFragment() {

    private lateinit var binding: LayoutImageFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LayoutImageFragmentBinding.inflate(inflater, container, false)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
        }
        initViews()
        return binding.root
    }

    private fun initViews() {
        arguments?.apply {
            getString(IMAGE_URL)?.apply {
                binding.ivImage.loadImageUri(this)
            }
        }
    }

    companion object {
        private const val IMAGE_URL: String = "IMAGE_URL"
        fun newInstance(imageUrl: String): ImageFragment = ImageFragment().apply {
            arguments = Bundle().apply {
                putString(IMAGE_URL, imageUrl)
            }
        }
    }
}
