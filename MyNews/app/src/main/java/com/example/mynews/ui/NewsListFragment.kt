package com.example.mynews.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mynews.MainViewModel
import com.example.mynews.R
import com.example.mynews.models.AbstractNewsDoc
import com.example.mynews.models.ArticleDiffCallback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_top_news.*
import kotlinx.android.synthetic.main.rv_item_article.view.*
import org.koin.android.viewmodel.ext.android.viewModel


class NewsListFragment : Fragment(R.layout.fragment_top_news) {

    private val viewModel by viewModel<MainViewModel>()
    private val TAG = "TopNewsFrag"
    var adapter: ArticleRvAdapter?  = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ArticleRvAdapter(viewModel, findNavController())
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(requireActivity())

        viewModel.getNewsFeed().observe(viewLifecycleOwner,
            Observer<Pair<Throwable?, List<AbstractNewsDoc>?>> { (error, data) ->
                if (data != null) {
                   adapter?.submitList(data)
                } else {
                    val msg = error?.message
                    Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
                }
            })

        arts.setOnClickListener { viewModel.getArts() }
        technology.setOnClickListener { viewModel.getTechnology() }
        real_estate.setOnClickListener { viewModel.getRealEstate()}
        automobiles.setOnClickListener { viewModel.getAutomobiles() }
        top_stories.setOnClickListener {viewModel.getTopStories()}
        most_popular.setOnClickListener { viewModel.getMostPopular() }
    }


    class ArticleRvAdapter(val viewModel: MainViewModel, val navController: NavController) : ListAdapter<AbstractNewsDoc, ArticleRvAdapter.VH>(
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
            holder.itemView.setOnClickListener{
                viewModel.setCurrentArticleUrl(getItem(position).getUrl())
                navController.navigate(R.id.readArticleFragment)
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.rv_item_article, parent, false)

            return VH(view)
        }
    }

}
