package com.lobo.userlist.view.avatars.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.lobo.userlist.R
import com.lobo.userlist.domain.Avatar
import com.lobo.userlist.view.avatars.AvatarOptions
import com.lobo.userlist.view.avatars.dialog.adapter.AvatarAdapter
import kotlinx.android.synthetic.main.avatar_bottom_sheet.*

class AvatarBottomSheetFragment : BottomSheetDialogFragment(), AvatarAdapter.AvatarListener {

    private lateinit var callback: AvatarAdapter.AvatarListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.avatar_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        avatarRecyclerView.layoutManager = GridLayoutManager(context, 3)
        avatarRecyclerView.adapter = AvatarAdapter(AvatarOptions.AVATARS, this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        try {
            callback = activity as AvatarAdapter.AvatarListener
        } catch (e: ClassCastException) {
            throw ClassCastException(activity.toString() + " must implement AvatarAdapter.AvatarListener")
        }
    }


    companion object {
        fun newInstance(): AvatarBottomSheetFragment {
            return AvatarBottomSheetFragment()
        }
    }

    override fun avatarClicked(avatar: Avatar) {
        callback.avatarClicked(avatar)
    }

}