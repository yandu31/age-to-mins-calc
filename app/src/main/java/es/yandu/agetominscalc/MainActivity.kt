package es.yandu.agetominscalc

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    /* Declare textViews that can be updated during the execution */
    private var tvSelectedDate : TextView? = null
    private var tvSelectedDateText : TextView? = null
    private var tvAgeInMinutes : TextView? = null
    private var tvAgeInMinutesText : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /* Initialize layout button and textViews */
        val btnDatePicker : Button = findViewById(R.id.btnDatePicker)
        tvSelectedDate = findViewById(R.id.tvSelectedDate)
        tvSelectedDateText = findViewById(R.id.tvSelectedDateText)
        tvAgeInMinutes = findViewById(R.id.tvAgeInMinutes)
        tvAgeInMinutesText = findViewById(R.id.tvAgeInMinutesText)

        btnDatePicker.setOnClickListener {
            clickDatePicker()
        }
    }

    /**
     * Opens a DatePicker dialog for the user to select a date, displays the selected date and
     * displays the time in minutes from the selected date to the current date.
     */
    private fun clickDatePicker(){
        /* Get current year, month and day for the DatePicker dialog */
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day =  myCalendar.get(Calendar.DAY_OF_MONTH)

        /* Create Date Picker Dialog and pass its Interface OnClickListener as lambda function */
        val dpd = DatePickerDialog(this,
            { _, selectedYear, selectedMonth, selectedDayOfMonth ->

                /* Optional: Display a toast indicating the year of the selected date */
                Toast.makeText(this,
                    "Year selected: $selectedYear", Toast.LENGTH_LONG).show()

                /* Get selected date formatted as string */
                val selectedDate = "$selectedDayOfMonth/${selectedMonth+1}/$selectedYear"

                /* Update the related textViews text and visibility */
                tvSelectedDate?.text = selectedDate
                tvSelectedDate?.visibility = View.VISIBLE
                tvSelectedDateText?.visibility = View.VISIBLE

                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)

                val theDate = sdf.parse(selectedDate)

                theDate?.let {
                    val selectedDateInMinutes = theDate.time / 60000

                    val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                    currentDate?.let {
                        val currentDateInMinutes = currentDate.time / 60000

                        /* Get selected date formatted as spring and update the related textViews
                         * text and visibility
                         */
                        val differenceInMinutes = currentDateInMinutes - selectedDateInMinutes
                        tvAgeInMinutes?.text = differenceInMinutes.toString()
                        tvAgeInMinutes?.visibility = View.VISIBLE
                        tvAgeInMinutesText?.visibility = View.VISIBLE
                    }
                }
            },
            year,
            month,
            day
        )

        dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000
        dpd.show()
    }
}
