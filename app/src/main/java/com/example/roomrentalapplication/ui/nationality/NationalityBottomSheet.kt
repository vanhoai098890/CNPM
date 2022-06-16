package com.example.roomrentalapplication.ui.nationality

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomrentalapplication.databinding.LayoutNationalityBottomSheetBinding
import com.example.roomrentalapplication.extensions.betterSmoothScrollToPosition
import com.example.roomrentalapplication.ui.base.BaseBottomSheetDialogFragment
import com.example.roomrentalapplication.ui.nationality.adapter.NationalityAdapter
import com.example.roomrentalapplication.ui.nationality.model.NationalityEnum
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NationalityBottomSheet : BaseBottomSheetDialogFragment() {

    private lateinit var binding: LayoutNationalityBottomSheetBinding
    private val viewModel: NationalityViewModel by viewModels()
    var onFirstItemClick: (NationalityEnum) -> Unit = {}
    private val nationalityAdapter: NationalityAdapter by lazy {
        NationalityAdapter().apply {
            onItemClick = {
                onFirstItemClick.invoke(it)
            }
        }
    }

    companion object {

        private const val NATIONALITY_ID = "NATIONALITY_ID"

        internal fun newInstance(id: Int): NationalityBottomSheet =
            NationalityBottomSheet().apply {
                arguments = Bundle().apply {
                    putInt(NATIONALITY_ID, id)
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LayoutNationalityBottomSheetBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        initViews()
        initEvents()
        return binding.root
    }

    private fun initEvents() {
    }

    private fun initViews() {
        heightOfDialog =
            (resources.displayMetrics.heightPixels - 50 * resources.displayMetrics.density).toInt()
        binding.apply {
            rvNationality.apply {
                layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

                arguments?.apply {
                    getInt(NATIONALITY_ID, -1).let { nationalityId ->
                        if (nationalityId != -1) {
                            nationalityAdapter.apply {
                                currentPosition = NationalityEnum.values()[nationalityId].apply {
                                    isSelected = true
                                }
                            }
                        }
                        adapter = nationalityAdapter
                        nationalityAdapter.submitList(viewModel.nationValues)
                        post {
                            if (nationalityId != -1) {
                                rvNationality.betterSmoothScrollToPosition(nationalityId)
                            }
                        }
                    }
                }
            }
        }
    }
}
