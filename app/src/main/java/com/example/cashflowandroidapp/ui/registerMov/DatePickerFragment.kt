package com.example.cashflowandroidapp.ui.registerMov

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.Calendar

class DatePickerFragment(val listener: (day:Int, month:Int, year:Int) -> Unit): DialogFragment(), DatePickerDialog.OnDateSetListener {

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        listener(p3, p2, p1)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val calendario = Calendar.getInstance()
        val day = calendario.get(Calendar.DAY_OF_MONTH)
        val month = calendario.get(Calendar.MONTH)
        val year = calendario.get(Calendar.YEAR)

        val picker = DatePickerDialog(activity as Context, this, year, month, day)
        picker.datePicker.maxDate = calendario.timeInMillis
        return picker
    }
}