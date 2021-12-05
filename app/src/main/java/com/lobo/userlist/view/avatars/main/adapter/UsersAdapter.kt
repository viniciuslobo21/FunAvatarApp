package com.lobo.userlist.view.avatars.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lobo.userlist.R
import com.lobo.userlist.databinding.UserItemBinding
import com.lobo.userlist.domain.UserModel

class UsersAdapter(
    private val context: Context,
    private val callback: Callback
//) : RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {
) : RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {

    private var items: List<UserModel> = emptyList()

    interface Callback {
        fun onItemClick(userIndex: Int)
        fun onRemoveClick(userIndex: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        UserViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.user_item, parent, false),
            callback::onItemClick,
            callback::onRemoveClick
        )

//    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
//        holder.bind(usersList[position], position)
//    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    private fun getItem(position: Int): UserModel = items[position]

    fun setData(data: List<UserModel>) {
        items = data
        notifyDataSetChanged()
    }

    inner class UserViewHolder internal constructor(
        itemView: View,
        private val onItemClick: (Int) -> Unit,
        private val onRemoveClick: (Int) -> Unit
    ) :
        RecyclerView.ViewHolder(itemView) {

        private var binding = UserItemBinding.bind(itemView)

        fun bind(item: UserModel, index: Int) {
            binding.user = item
            binding.containerMainInfo.setOnClickListener {
                onItemClick(index)
            }
            binding.imageDelete.setOnClickListener {
                onRemoveClick(index)
            }
        }
    }

    override fun getItemCount(): Int = items.size
}

object UsersListItemDiffCallback : DiffUtil.ItemCallback<UserModel>() {
    override fun areItemsTheSame(
        oldItem: UserModel,
        newItem: UserModel
    ): Boolean = oldItem == newItem

    override fun areContentsTheSame(
        oldItem: UserModel, newItem: UserModel
    ): Boolean = oldItem == newItem
}