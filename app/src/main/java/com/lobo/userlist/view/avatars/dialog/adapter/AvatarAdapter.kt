package com.lobo.userlist.view.avatars.dialog.adapter

import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.lobo.userlist.R
import com.lobo.userlist.domain.Avatar

class AvatarAdapter(private val avatars: List<Avatar>, private val listener: AvatarListener) :
    RecyclerView.Adapter<AvatarAdapter.ViewHolder>() {

    interface AvatarListener {
        fun avatarClicked(avatar: Avatar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.avatar_item, parent, false)
        )
    }

    override fun getItemCount() = avatars.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(avatars[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        private lateinit var avatar: Avatar

        private val imageView = itemView.findViewById<ImageView>(R.id.avatar)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(avatar: Avatar) {
            this.avatar = avatar
            val bitmap = BitmapFactory.decodeResource(
                imageView.context.resources,
                avatar.drawable
            )
            imageView.setImageDrawable(BitmapDrawable(imageView.context.resources, bitmap))
        }

        override fun onClick(view: View) {
            listener.avatarClicked(this.avatar)
        }
    }
}