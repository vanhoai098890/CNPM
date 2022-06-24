package com.example.roomrentalapplication.ui.add_room

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
import com.example.roomrentalapplication.databinding.AddRoomFragmentBinding
import com.example.roomrentalapplication.extensions.setSafeOnClickListener
import com.example.roomrentalapplication.ui.add_property.GridImageAdapter
import com.example.roomrentalapplication.ui.add_room.adapter.AddServiceAdapter
import com.example.roomrentalapplication.ui.base.BaseFragment
import com.example.roomrentalapplication.utils.LogUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.File

@AndroidEntryPoint
class AddRoomFragment : BaseFragment() {

    private lateinit var binding: AddRoomFragmentBinding
    private val viewModel: AddRoomViewModel by viewModels()
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

    private val addServiceAdapter: AddServiceAdapter by lazy {
        AddServiceAdapter().apply {
            onRemoveAction = {
                viewModel.serviceList.value.removeAt(it)
                binding.root.post {
                    addServiceAdapter.notifyItemRemoved(it)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AddRoomFragmentBinding.inflate(inflater, container, false)
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
                tvCenter.text = getString(R.string.v1_add_room)
            }

            btnCreateRoom.setSafeOnClickListener {
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
            rvServices.apply {
                layoutManager = GridLayoutManager(requireContext(), 2)
                adapter = addServiceAdapter
                addServiceAdapter.submitList(viewModel.serviceList.value)
            }
            rvImage.apply {
                layoutManager = GridLayoutManager(requireContext(), 3)
                adapter = gridImageAdapter
                gridImageAdapter.submitList(viewModel.imageList.value)
            }
            radioButtonPrice.setOnCheckedChangeListener { group, checkedId ->
                when (checkedId) {
                    R.id.btnYes -> {
                        viewModel.stateCheckRadio.value = 1
                    }
                    R.id.btnNo -> {
                        viewModel.stateCheckRadio.value = 0
                    }
                }
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
                        Toast.makeText(
                            requireContext(),
                            "Create room successfully!!",
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
            launch {
                viewModel.stateSetDropMenu.collect {
                    if (it) {
                        binding.apply {
                            val typeAdapter = ArrayAdapter(
                                requireContext(),
                                R.layout.layout_item_text,
                                viewModel.statePropertiesName.value
                            )
                            edtPropertyName.apply {
                                setAdapter(typeAdapter)
                                setOnItemClickListener { _, _, position, _ ->
                                    viewModel.statePropertySelected.value =
                                        viewModel.stateProperties.value[position].propertyId
                                            ?: -1
                                }
                            }
                        }
                        viewModel.stateSetDropMenu.value = false
                    }
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
