package com.example.mynews.ui.newsList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mynews.MainViewModel
import com.example.mynews.R
import com.example.mynews.models.AbstractNewsDoc
import kotlinx.android.synthetic.main.fragment_top_news.*
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class NewsListFragment : Fragment(R.layout.fragment_top_news) {

    private val viewModel by viewModel<MainViewModel>()
    private val TAG = "TopNewsFrag"
    var adapter: ArticleRvAdapter?  = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ArticleRvAdapter()
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
}
