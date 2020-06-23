package com.example.mynews.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.widget.CheckBox
import android.widget.CompoundButton
import androidx.lifecycle.Observer
import com.example.mynews.viewmodels.NotificationsViewModel
import com.example.mynews.R
import kotlinx.android.synthetic.main.fragment_notification.*
import kotlinx.android.synthetic.main.fragment_search.search_term
import kotlinx.android.synthetic.main.include_search_checkboxes.*
import org.koin.android.viewmodel.ext.android.viewModel

class NotificationFragment : Fragment(R.layout.fragment_notification) {
    private val viewModel by viewModel<NotificationsViewModel>()
    lateinit var filters: List<Pair<CheckBox, String>>
    private var doFilterUpdate = false

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        latchUiElements()
        bindObservers()
        bindListeners()

    }

    fun latchUiElements() {
        filters = listOf(
            Pair(arts, "arts"),
            Pair(business, "business"),
            Pair(politics, "politics"),
            Pair(sports, "sports"),
            Pair(entrepreneurs, "entrepreneurs"),
            Pair(travel, "travel")
        )
    }

    fun bindObservers() {
        viewModel.getNotificationQuery().observe(viewLifecycleOwner, Observer {
            search_term.setText(it)
        })
        viewModel.getNotificationFilters().observe( viewLifecycleOwner, Observer { activeFilters ->
            doFilterUpdate = false
            filters.forEach {
                    (checkbox, name) ->
                if (activeFilters.contains(name)) {
                    checkbox.isChecked = true
                } else {
                    checkbox.isChecked = false
                }
            }
            doFilterUpdate = true
        })
        viewModel.getNotificationsActive().observe(viewLifecycleOwner, Observer{
            doFilterUpdate = false
            notification_switch.isChecked = it
            doFilterUpdate = true
        })
    }

    fun bindListeners() {
        notification_switch.setOnCheckedChangeListener(
            object: CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                //TODO call this code when we leave the fragment in order to update
                // filters and query, where the switch wasn't cycled.
                val query = search_term.text.toString()
                if (doFilterUpdate) {
                    viewModel.setNotificationFilters(filters
                        .filter { (checkbox, _) -> checkbox.isChecked }
                        .map { (_, filterName) -> filterName }
                    )
                    viewModel.setNotificationQuery(query)
                    viewModel.setNotificationsActive(isChecked)
                }
            }
        })
    }
}