package com.example.palindromeapp.ui.thridscreen

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.palindromeapp.R
import com.example.palindromeapp.data.remote.response.DataItem
import com.example.palindromeapp.databinding.ItemUserBinding

class UserAdapter(private val onItemClick: (DataItem) -> Unit) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private val userList = mutableListOf<DataItem>()

    @SuppressLint("NotifyDataSetChanged")
    fun setUsers(users: List<DataItem>, isRefreshing: Boolean = false) {
        if (isRefreshing) {
            userList.clear()
            userList.addAll(users)
            notifyDataSetChanged()
        } else {
            val previousSize = userList.size
            userList.addAll(users)
            notifyItemRangeInserted(previousSize, users.size)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(userList[position])
    }

    override fun getItemCount(): Int = userList.size

    inner class UserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: DataItem) {
            val fullName = listOfNotNull(user.firstName?.trim(), user.lastName?.trim())
                .joinToString(" ")
            binding.tvName.text = fullName
            binding.tvEmail.text = user.email.orEmpty()
            Glide.with(binding.root.context)
                .load(user.avatar)
                .placeholder(R.drawable.avatar)
                .into(binding.ivUser)

            binding.root.setOnClickListener { onItemClick(user) }
        }
    }
}