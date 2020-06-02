package com.example.mynews.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mynews.viewmodels.MainViewModel
import com.example.mynews.R
import com.example.mynews.models.AbstractArticle
import kotlinx.android.synthetic.main.fragment_top_news.*
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class NewsListFragment : Fragment() {

    private val viewModel by viewModel<MainViewModel>()
    private val TAG = "TopNewsFrag"
    var adapter: ArticleRvAdapter?  = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_top_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ArticleRvAdapter()
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(requireActivity())

        viewModel.newsFeed.observe(viewLifecycleOwner,
            Observer<Pair<Throwable?, List<AbstractArticle>?>> { (error, data) ->
                if (data != null) {
                   adapter?.submitList(data)
                } else {
                    val msg = error?.message
                }
            })

        arts.setOnClickListener { viewModel.getArts() }
        technology.setOnClickListener { viewModel.getTechnology() }
        real_estate.setOnClickListener { viewModel.getRealEstate()}
        automobiles.setOnClickListener { viewModel.getAutomobiles() }
        top_stories.setOnClickListener {viewModel.getTopStories()}
        most_popular.setOnClickListener { viewModel.getMostPopular() }
    }
}
