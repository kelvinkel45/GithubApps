package com.example.githubapps.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubapps.data.response.UserResponse
import com.example.githubapps.databinding.ItemUsersBinding

class UserAdapter (
    private val onClick: (UserResponse) -> Unit
        ) : ListAdapter<UserResponse, UserAdapter.UserViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(onClick, binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class UserViewHolder(
        val onClick: (UserResponse) -> Unit,
        private val binding: ItemUsersBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onClick(currentList[adapterPosition])
            }
        }

        fun bind(item: UserResponse){
            binding.tvUsername.text = item.username
            Glide.with(binding.imgAvatar)
                .load(item.avatarUrl)
                .centerCrop()
                .into(binding.imgAvatar)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UserResponse>() {
            override fun areItemsTheSame(oldItem: UserResponse, newItem: UserResponse): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: UserResponse, newItem: UserResponse): Boolean {
                return oldItem == newItem
            }
        }
    }
}