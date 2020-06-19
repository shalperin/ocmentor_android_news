package com.example.mynews.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        createDatePickerDialogs()
        bindObservers()
        bindListeners()
        Log.d("FRAGAMEMNON", "hello world")
        Log.d("FRAGAMEMNON", viewModel.getSearchBeginDate().value.toString())
    }

    fun bindObservers() {
        viewModel.getSearchBeginDate().observe(viewLifecycleOwner, Observer {
            begin_date.text = it.toString("MM/dd/YYYY") })

        viewModel.getSearchEndDate().observe(viewLifecycleOwner, Observer {
            end_date.text = it.toString("MM/dd/YYYY") })
    }

    fun bindListeners() {
        search_btn.setOnClickListener(searchButtonOnClickListener)
        search_term.addTextChangedListener(searchTermOnTextChangeListener)
        begin_date.setOnClickListener { beginDatePickerDialog.show() }
        end_date.setOnClickListener { endDatePickerDialog.show() }
        arts.setOnClickListener(filterCheckboxOnClickListener)
        business.setOnClickListener(filterCheckboxOnClickListener)
        politics.setOnClickListener(filterCheckboxOnClickListener)
        sports.setOnClickListener(filterCheckboxOnClickListener)
        entrepreneurs.setOnClickListener(filterCheckboxOnClickListener)
        travel.setOnClickListener(filterCheckboxOnClickListener)

    }



    val searchButtonOnClickListener = View.OnClickListener {
        val query = search_term.text.toString()
        if (query.isEmpty()) {
            Toast.makeText(requireContext(), "Please enter a search term.", Toast.LENGTH_LONG)
                .show()
        } else {
            viewModel.submitSearch()
            findNavController().navigate(R.id.topNewsFragment)
        }
    }

    val searchTermOnTextChangeListener = object: TextWatcher {
        override fun afterTextChanged(s: Editable?) {}
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            viewModel.setSearchQuery(s.toString())
        }
    }

    val filterCheckboxOnClickListener = View.OnClickListener {
        val filters = listOf(
            Pair(arts, "arts"),
            Pair(business, "business"),
            Pair(politics, "politics"),
            Pair(sports, "sports"),
            Pair(entrepreneurs, "entrepreneurs"),
            Pair(travel, "travel")
        )
        val stringArray = filters
            .filter { (checkbox, _) -> checkbox.isChecked }
            .map { (_, filterName) -> filterName }

        viewModel.setSearchFilters(stringArray)
    }

    fun createDatePickerDialogs() {
        beginDatePickerDialog = object: DatePickerDialog(requireContext()) {
            override fun onDateChanged(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
                super.onDateChanged(view, year, month, dayOfMonth)
                viewModel.setSearchBeginDate(
                    DateTime()
                        .withYear(year)
                        .withMonthOfYear(month)
                        .withDayOfMonth(dayOfMonth)

                )
            }
        }

        endDatePickerDialog = object: DatePickerDialog(requireContext()) {
            override fun onDateChanged(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
                super.onDateChanged(view, year, month, dayOfMonth)
                viewModel.setSearchEndDate(
                    DateTime()
                        .withYear(year)
                        .withMonthOfYear(month)
                        .withDayOfMonth(dayOfMonth)

                )
            }
        }
    }



}