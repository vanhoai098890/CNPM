package com.example.roomrentalapplication.ui.add_property

import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.roomrentalapplication.R
import com.example.roomrentalapplication.databinding.FragmentAddPropertyBinding
import com.example.roomrentalapplication.extensions.setSafeOnClickListener
import com.example.roomrentalapplication.ui.base.BaseFragment
import com.example.roomrentalapplication.utils.LogUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.File

@AndroidEntryPoint
class AddPropertyFragment : BaseFragment() {

    private lateinit var binding: FragmentAddPropertyBinding
    private val viewModel: AddPropertyViewModel by viewModels()
    private val activityResultLauncher =
        registerForActivityResult(
            ActivityResultContracts.GetContent()
        ) { result ->
            result?.apply {
                LogUtils.d(result.toString())
                viewModel.imageList.value.add(viewModel.imageList.value.size - 1, result)
                gridImageAdapter.notifyItemInserted(viewModel.imageList.value.size - 1)
            }
        }

    private val gridImageAdapter: GridImageAdapter by lazy {
        GridImageAdapter().apply {
            onAddAction = {
                activityResultLauncher.launch("image/*")
            }
            onRemoveAction = {
                viewModel.imageList.value.removeAt(it)
                gridImageAdapter.notifyItemRemoved(it)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddPropertyBinding.inflate(inflater, container, false)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            model = viewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initEvents()
    }

    private fun initViews() {
        binding.apply {
            layoutToolbar.apply {
                backButton.setSafeOnClickListener {
                    handleBackPressed()
                }
                tvCenter.text = getString(R.string.v1_add_property)
            }
            val types = resources.getStringArray(R.array.property_type)
            val typeAdapter = ArrayAdapter(requireContext(), R.layout.layout_item_text, types)
            edtPropertyType.apply {
                setAdapter(typeAdapter)
                setOnItemClickListener { _, _, position, _ ->
                    viewModel.statePropertyType.value = position + 1
                }
            }
            gvImage.layoutManager = GridLayoutManager(requireContext(), 3)
            gvImage.adapter = gridImageAdapter
            gridImageAdapter.submitList(viewModel.imageList.value)

            btnCreateProperty.setSafeOnClickListener {
                val images = mutableListOf<File>()
                viewModel.imageList.value.forEach {
                    it?.let { uri ->
                        getRealPathFromURI(uri)?.apply {
                            images.add(File(this))
                        }
                    }
                }
                viewModel.submit(images)
            }
        }
    }

    private fun initEvents() {
        lifecycleScope.launchWhenStarted {
            launch {
                viewModel.stateErrorSubmit.collect {
                    if (it) {
                        Toast.makeText(
                            requireContext(),
                            "Something fields is wrong currently!!!",
                            Toast.LENGTH_SHORT
                        ).show()
                        viewModel.stateErrorSubmit.value = false
                    }
                }
            }
            launch {
                viewModel.stateSuccess.collect {
                    if (it) {
                        delay(500)
                        Toast.makeText(
                            requireContext(),
                            "Create property successfully!!",
                            Toast.LENGTH_SHORT
                        ).show()
                        handleBackPressed()
                    }
                }
            }
            launch {
                viewModel.loadingState().collect {
                    handleShowLoadingDialog(it)
                }
            }
        }
    }

    private fun getRealPathFromURI(contentUri: Uri): String? {
        var res: String? = null
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = requireActivity().contentResolver.query(contentUri, proj, null, null, null)
        cursor?.apply {
            if (cursor.moveToFirst()) {
                val index: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                res = cursor.getString(index)
            }
            cursor.close()
        } ?: kotlin.run {
            res = contentUri.path
        }
        return res
    }
}
