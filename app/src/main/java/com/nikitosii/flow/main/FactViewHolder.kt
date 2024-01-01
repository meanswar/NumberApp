package com.nikitosii.flow.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nikitosii.core.database.entity.NumberFact
import com.nikitosii.databinding.ItemFactHistoryBinding
import com.nikitosii.util.ext.onClick

class FactViewHolder private constructor(
    private val binding: ItemFactHistoryBinding,
    private val action: (NumberFact) -> Unit
    ) :
    RecyclerView.ViewHolder(binding.root) {

        fun bind(fact: NumberFact) {
            binding.fact = fact
            binding.clContent.onClick { action(fact) }
        }

    companion object {
        fun from(parent: ViewGroup, onCLick: (NumberFact) -> Unit): FactViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemFactHistoryBinding.inflate(layoutInflater, parent, false)
            return FactViewHolder(binding, onCLick)
        }
    }
}