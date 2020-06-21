package com.example.mynews.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.mynews.viewmodels.MainViewModel
import com.example.mynews.R
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val nav = findNavController(R.id.nav_host_fragment)

        viewModel.articlesLoading().observe(this, Observer { loading ->
            if (loading) {
                loading_spinner.visibility = View.VISIBLE
            } else {
                loading_spinner.visibility = View.GONE
            }
        })

        search_btn.setOnClickListener { nav.navigate(R.id.search) }

        back_arrow.setOnClickListener {
            nav.navigate(R.id.topNewsFragment)

        }

        notification_button.setOnClickListener{nav.navigate(R.id.notificationFragment)
        }

        findNavController(R.id.nav_host_fragment).addOnDestinationChangedListener(destinationChangedListener)

    }

    private val destinationChangedListener = NavController.OnDestinationChangedListener { _, destination, _ ->
        when (destination.id) {
            R.id.topNewsFragment -> {
                toolbar.visibility = View.VISIBLE
                aux_toolbar.visibility = View.INVISIBLE
            }
            R.id.search -> {
                toolbar.visibility = View.INVISIBLE
                aux_toolbar.visibility = View.VISIBLE
                aux_toolbar_text.text = "Search"
            }
            R.id.notificationFragment -> {
                toolbar.visibility = View.INVISIBLE
                aux_toolbar.visibility = View.VISIBLE
                aux_toolbar_text.text = "Notifications"
            }
            R.id.readArticleFragment -> {
                toolbar.visibility = View.INVISIBLE
                aux_toolbar.visibility = View.VISIBLE
                aux_toolbar_text.text = ""
            }
        }
    }
}
