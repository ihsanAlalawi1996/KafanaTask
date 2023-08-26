package com.example.ihsanstask.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.ihsanstask.R
import com.example.ihsanstask.databinding.DateTimePickerDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.Calendar
import java.util.Locale

enum class PickerType { YEAR, MONTH, DAY, DATE, TIME }

class DateTimeDialog(
    private val startMonth: Int = 1,
    private val startYear: Int = 2023,
    private val endYear: Int = Calendar.getInstance(Locale("en"))[Calendar.YEAR],
    private val pickerType: PickerType,
    private val func: (Int, Int, Int) -> Unit
) : BottomSheetDialogFragment() {

    private lateinit var binding: DateTimePickerDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.date_time_picker_dialog, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cal = Calendar.getInstance(Locale("en"))

        binding.apply {
            when (pickerType) {
                PickerType.YEAR -> {
                    yearPicker.visibility = View.VISIBLE
                    monthPicker.visibility = View.GONE
                    dayPicker.visibility = View.GONE
                    hourPicker.visibility = View.GONE
                    minutePicker.visibility = View.GONE
                }

                PickerType.MONTH -> {
                    yearPicker.visibility = View.GONE
                    monthPicker.visibility = View.VISIBLE
                    dayPicker.visibility = View.GONE
                    hourPicker.visibility = View.GONE
                    minutePicker.visibility = View.GONE
                }

                PickerType.DAY -> {
                    yearPicker.visibility = View.GONE
                    monthPicker.visibility = View.GONE
                    dayPicker.visibility = View.VISIBLE
                    hourPicker.visibility = View.GONE
                    minutePicker.visibility = View.GONE
                }

                PickerType.DATE -> {
                    yearPicker.visibility = View.VISIBLE
                    monthPicker.visibility = View.VISIBLE
                    dayPicker.visibility = View.VISIBLE
                    hourPicker.visibility = View.GONE
                    minutePicker.visibility = View.GONE
                }

                PickerType.TIME -> {
                    yearPicker.visibility = View.GONE
                    monthPicker.visibility = View.GONE
                    dayPicker.visibility = View.GONE
                    hourPicker.visibility = View.VISIBLE
                    minutePicker.visibility = View.VISIBLE
                }
            }

            dayPicker.minValue = 1
            dayPicker.maxValue = 31
            dayPicker.value = cal[Calendar.DAY_OF_MONTH]

            monthPicker.maxValue = 12
            yearPicker.minValue = startYear
            yearPicker.maxValue = cal[Calendar.YEAR]
            yearPicker.value = cal[Calendar.YEAR]
            monthPicker.minValue = if (endYear > startYear) 1 else startMonth
            monthPicker.value = cal[Calendar.MONTH] + 1

            if (hourPicker.visibility == View.VISIBLE) {
                hourPicker.minValue = 1
                hourPicker.maxValue = 12
                hourPicker.value = cal[Calendar.HOUR]

                minutePicker.minValue = 1
                minutePicker.maxValue = 60
                minutePicker.value = cal[Calendar.MINUTE]
            }

            close.setOnClickListener {
                dismiss()
            }

            select.setOnClickListener {
                if (pickerType == PickerType.TIME) {
                    func(hourPicker.value, minutePicker.value, 0)

                } else
                    func(yearPicker.value, monthPicker.value, dayPicker.value)
                dismiss()
            }
        }
    }
}