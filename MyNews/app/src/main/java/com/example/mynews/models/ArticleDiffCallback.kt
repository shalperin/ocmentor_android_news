package com.example.mynews.models

import androidx.recyclerview.widget.DiffUtil
import com.example.mynews.models.topstories.Article

open class ArticleDiffCallback: DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.uri == newItem.uri
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }
}