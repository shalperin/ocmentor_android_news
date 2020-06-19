package com.example.mynews.models

import androidx.recyclerview.widget.DiffUtil

open class ArticleDiffCallback: DiffUtil.ItemCallback<AbstractNewsDoc>() {
    override fun areItemsTheSame(oldItem: AbstractNewsDoc, newItem: AbstractNewsDoc): Boolean {
        return oldItem.getUri() == newItem.getUri()
    }

    override fun areContentsTheSame(oldItem: AbstractNewsDoc, newItem: AbstractNewsDoc): Boolean {
        return oldItem == newItem
    }
}