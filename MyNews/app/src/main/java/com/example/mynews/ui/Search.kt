package com.example.mynews.ui

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.mynews.R
import com.example.mynews.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.fragment_search.*
import org.joda.time.DateTime
import org.koin.android.viewmodel.ext.android.viewModel

class Search : Fragment(R.layout.fragment_search) {

    private val viewModel by viewModel<MainViewModel>()
    lateinit var beginDatePickerDialog:DatePickerDialog
    lateinit var endDatePickerDialog: DatePickerDialog
    lateinit var filters: List<Pair<CheckBox, String>>

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        createDatePickerDialogs()
        latchUiElements()
        bindObservers()
        bindListeners()
       }

    fun bindObservers() {
        viewModel.getSearchBeginDate().observe(viewLifecycleOwner, Observer {
            begin_date.text = it?.toString("MM/dd/YYYY") })

        viewModel.getSearchEndDate().observe(viewLifecycleOwner, Observer {
            end_date.text = it?.toString("MM/dd/YYYY") })

        viewModel.getSearchQuery().observe(viewLifecycleOwner, Observer {
            search_term.setText(it)
        })

        viewModel.getFilters().observe( viewLifecycleOwner, Observer { activeFilters ->
            filters.forEach {
                    (checkbox, name) ->
                        if (activeFilters.contains(name)) {
                            checkbox.isChecked = true
                        } else {
                            checkbox.isChecked = false
                        }
            }
        })
    }

    fun bindListeners() {
        search_btn.setOnClickListener(searchButtonOnClickListener)
        begin_date.setOnClickListener { beginDatePickerDialog.show() }
        end_date.setOnClickListener { endDatePickerDialog.show() }
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



    val searchButtonOnClickListener = View.OnClickListener {
        val query = search_term.text.toString()
        viewModel.setSearchFilters(filters
            .filter { (checkbox, _) -> checkbox.isChecked }
            .map { (_, filterName) -> filterName }
        )
        viewModel.setSearchQuery(query)
        viewModel.submitSearch()
        findNavController().navigate(R.id.topNewsFragment)

    }

    fun createDatePickerDialogs() {
        beginDatePickerDialog = object: DatePickerDialog(requireContext()) {
            override fun onDateChanged(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
                super.onDateChanged(view, year, month, dayOfMonth)
                viewModel.setSearchBeginDate(
                    DateTime()
                        .withYear(year)
                        .withMonthOfYear(month + 1)
                        .withDayOfMonth(dayOfMonth)

                )
            }
        }

        beginDatePickerDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Clear", object: DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                viewModel.setSearchBeginDate(null)
            }
        })

        endDatePickerDialog = object: DatePickerDialog(requireContext()) {
            override fun onDateChanged(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
                super.onDateChanged(view, year, month, dayOfMonth)
                viewModel.setSearchEndDate(
                    DateTime()
                        .withYear(year)
                        .withMonthOfYear(month + 1)
                        .withDayOfMonth(dayOfMonth)

                )
            }
        }

        endDatePickerDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Clear", object: DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                viewModel.setSearchBeginDate(null)
            }
        })
    }



}