package com.example.mynews.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.mynews.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        search_btn.setOnClickListener {
            findNavController(R.id.nav_host_fragment).navigate(R.id.search)
        }

        search_arrow.setOnClickListener {
            findNavController(R.id.nav_host_fragment).navigate(R.id.topNewsFragment)

        }

        findNavController(R.id.nav_host_fragment).addOnDestinationChangedListener(destinationChangedListener)

    }

    private val destinationChangedListener = NavController.OnDestinationChangedListener { _, destination, _ ->
        when (destination.id) {
            R.id.topNewsFragment -> {
                toolbar.visibility = View.VISIBLE
                search_toolbar.visibility = View.INVISIBLE
            }
            R.id.search -> {
                toolbar.visibility = View.INVISIBLE
                search_toolbar.visibility = View.VISIBLE
            }
        }
    }
}
