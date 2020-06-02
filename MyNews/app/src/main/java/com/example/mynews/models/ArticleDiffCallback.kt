package com.example.mynews.models

import androidx.recyclerview.widget.DiffUtil

open class ArticleDiffCallback: DiffUtil.ItemCallback<AbstractArticle>() {
    override fun areItemsTheSame(oldItem: AbstractArticle, newItem: AbstractArticle): Boolean {
        return oldItem.getUri() == newItem.getUri()
    }

    override fun areContentsTheSame(oldItem: AbstractArticle, newItem: AbstractArticle): Boolean {
        return oldItem == newItem
    }
}