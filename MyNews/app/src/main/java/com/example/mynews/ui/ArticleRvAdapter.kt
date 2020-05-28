package com.example.mynews.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mynews.R
import com.example.mynews.models.topstories.Article
import com.example.mynews.models.ArticleDiffCallback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.rv_item_article.view.*


class ArticleRvAdapter() : ListAdapter<Article, ArticleRvAdapter.VH > (
    ArticleDiffCallback()
) {

    inner class VH(val v: View): RecyclerView.ViewHolder(v) {
        fun bind(article: Article)
        {
            v.title.text = article.title
            v.date.text = article.getHumanizedPublishedDate()
            v.section.text = article.section
            Picasso.get().load(article.getStandardThumb()).into(v.thumbnail)
        }
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.rv_item_article, parent, false)
        return VH(view)
    }
}
