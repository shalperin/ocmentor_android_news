package com.example.mynews.ui.newsList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mynews.R
import com.example.mynews.models.ArticleDiffCallback
import com.example.mynews.models.AbstractNewsDoc
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.rv_item_article.view.*


class ArticleRvAdapter() : ListAdapter<AbstractNewsDoc, ArticleRvAdapter.VH> (
    ArticleDiffCallback()
) {

    inner class VH(val v: View): RecyclerView.ViewHolder(v) {
        fun bind(article: AbstractNewsDoc)
        {
            v.title.text = article.getTitle()
            v.date.text = article.getHumanizedPublishedDate()
            v.section.text = article.getSectionOrType()
            if (article.getThumbnailUrl() != null) {
                Picasso.get().load(article.getThumbnailUrl()).into(v.thumbnail)
            }
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
