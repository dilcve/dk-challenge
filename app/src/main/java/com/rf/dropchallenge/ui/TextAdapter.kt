package com.rf.dropchallenge.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rf.dropchallenge.R
import com.rf.dropchallenge.databinding.ListItemTextBinding

class TextAdapter :
    RecyclerView.Adapter<TextAdapter.TextViewHolder>() {

    private var text: List<String> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextViewHolder {
        val binding =
            ListItemTextBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TextViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TextViewHolder, position: Int) {
        holder.bind(text[position])
    }

    override fun getItemCount(): Int = text.size

    class TextViewHolder(
        private val binding: ListItemTextBinding
    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.btnAction.setOnClickListener(this)
        }

        fun bind(text: String) {
            binding.txtInfo.text = text
        }

        override fun onClick(v: View?) {
            binding.btnAction.setText(
                if (binding.btnAction.text == binding.root.context.getText(R.string.btn_idle)) R.string.btn_done else R.string.btn_idle
            )

        }
    }

    fun setItems(items: List<String>) {
        this.text = items
        notifyDataSetChanged()
    }
}
