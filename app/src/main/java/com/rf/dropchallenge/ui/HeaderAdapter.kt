package com.rf.dropchallenge.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rf.dropchallenge.databinding.ListItemHeaderBinding

class HeaderAdapter(private val header: String) :
    RecyclerView.Adapter<HeaderAdapter.HeaderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeaderViewHolder {
        val binding =
            ListItemHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HeaderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HeaderViewHolder, position: Int) {
        holder.bind(header)
    }

    override fun getItemCount(): Int = 1

    class HeaderViewHolder(
        private val binding: ListItemHeaderBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(header: String) {
            binding.txtHeader.text = header
        }
    }
}
