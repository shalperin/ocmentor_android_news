package com.example.mynews.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.mynews.MainViewModel
import com.example.mynews.R
import kotlinx.android.synthetic.main.fragment_read_article.*
import org.koin.android.viewmodel.ext.android.viewModel

class ReadArticleFragment : Fragment(R.layout.fragment_read_article) {
    private val viewModel by viewModel<MainViewModel>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.getCurrentArticleUrl().observe(viewLifecycleOwner, Observer {
            article_read_web_view.loadUrl(it)
        })
    }
}