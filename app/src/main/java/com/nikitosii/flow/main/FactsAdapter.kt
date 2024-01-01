package com.nikitosii.flow.main

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.nikitosii.core.database.entity.NumberFact

class FactsAdapter(private val onClick: (NumberFact) -> Unit) :
    ListAdapter<NumberFact, FactViewHolder>(FactAdapterCallback) {

    object FactAdapterCallback : DiffUtil.ItemCallback<NumberFact>() {
        override fun areItemsTheSame(oldItem: NumberFact, newItem: NumberFact): Boolean {
            return oldItem.number == newItem.number
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: NumberFact, newItem: NumberFact): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FactViewHolder {
       return FactViewHolder.from(parent, onClick)
    }

    override fun onBindViewHolder(holder: FactViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}