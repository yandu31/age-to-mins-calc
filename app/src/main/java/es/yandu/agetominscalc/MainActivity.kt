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
    private var tvSelectedDate: TextView? = null
    private var tvSelectedDateText: TextView? = null
    private var tvAgeInMinutes: TextView? = null
    private var tvAgeInMinutesText: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /* Initialize layout button and textViews */
        val btnDatePicker: Button = findViewById(R.id.btnDatePicker)
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

                /* Create a SimpleDateFormat pattern and parse the selected date to a Date instance */
                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
                val theDate = sdf.parse(selectedDate) // Returns Date object from string

                theDate?.let { // Run this block only if theDate is not null
                    /* Get time from Unix epoch to the selected date */
                    val selectedDateInMinutes = theDate.time / 60000 // Convert ms to mins

                    /* TODO check possible redundancy lines 72 and line 76 */
                    val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                    
                    currentDate?.let { // Run this block only if currentDate is not null
                        /* Get time from Unix epoch to the current date */
                        val currentDateInMinutes = currentDate.time / 60000 // Convert ms to mins

                        val differenceInMinutes = currentDateInMinutes - selectedDateInMinutes
                        
                        /* Update the related textViews text and their visibility so they appear */
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

        /* Set the current date as upper limit to the available dates to pick, and show the dialog */
        dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000
        dpd.show()
    }
}
