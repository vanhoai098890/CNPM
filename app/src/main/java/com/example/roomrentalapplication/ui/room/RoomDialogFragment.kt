package com.example.roomrentalapplication.ui.room

import android.app.Dialog
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import com.example.roomrentalapplication.R
import com.example.roomrentalapplication.extensions.setSafeOnClickListener
import com.example.roomrentalapplication.ui.base.BaseDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RoomDialogFragment : BaseDialogFragment() {

    private lateinit var ivBack: ImageView
    private lateinit var ivFavourite: ImageView

    override fun setContentDialog(dialog: Dialog) {
        dialog.apply {
            setContentView(R.layout.layout_room_dialog)
            ivBack = findViewById(R.id.ivBack)
            ivFavourite = findViewById(R.id.ivFavourite)
        }
    }

    override fun initListeners(dialog: Dialog) {
        ivFavourite.setSafeOnClickListener {
            ivFavourite.isSelected = !ivFavourite.isSelected
        }
        ivBack.setSafeOnClickListener {
            dismiss()
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
            setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
            )
        }
    }

    companion object {

        private const val ROOM_ID = "ROOM_ID"
        private const val NONE_ROOM = -1

        fun newInstance(roomId: Int?): RoomDialogFragment {
            val args = Bundle()
            args.putInt(ROOM_ID, roomId ?: NONE_ROOM)
            val fragment = RoomDialogFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
