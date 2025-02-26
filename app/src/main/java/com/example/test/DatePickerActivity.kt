package com.example.test

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.app.DatePickerDialog
import android.os.Build
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.loper7.date_time_picker.dialog.CardDatePickerDialog
import java.util.*
import com.jakewharton.threetenabp.AndroidThreeTen
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import java.text.SimpleDateFormat
import java.util.*
class DatePickerActivity : AppCompatActivity() {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    var date = LocalDateTime.now().format(formatter)
    var date2 = LocalDateTime.now().format(formatter2)

    lateinit var selectedDateTextView: TextView
    lateinit var selectedDateTextView2: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_date_picker)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.selectDateButton)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        AndroidThreeTen.init(this)

        val selectDateButton = findViewById<Button>(R.id.selectDateButton)
        selectedDateTextView = findViewById<TextView>(R.id.selectedDateTextView)
        selectedDateTextView.text = date
        selectDateButton.setOnClickListener {
            openDatePicker()
        }

        val selectDateButton2 = findViewById<Button>(R.id.selectDateButton2)
        selectedDateTextView2 = findViewById<TextView>(R.id.selectedDateTextView2)
        selectedDateTextView2.text = date2
        selectDateButton2.setOnClickListener {
            openDatePicker2()
        }
    }

    private fun openDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog (
            this,
            { _, seletedYear, selectedMonth, selectedDay ->
                var month = (selectedMonth + 1).toString().padStart(2, '0')
                var day = selectedDay.toString().padStart(2, '0')
                val selectedDate = "$seletedYear-${month}-$day"
                date = selectedDate
                selectedDateTextView.text = date
            }, year, month, day
        )
        datePickerDialog.show()
    }

    private fun openDatePicker2() {
        // 创建 SimpleDateFormat 对象
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

        // 解析字符串为 Date 对象
        val date = simpleDateFormat.parse(date2)

        // 获取毫秒数（Long 类型）
        val timestampMillis: Long = date?.time ?: 0L


        CardDatePickerDialog.builder(this)
            .setTitle("SET MAX DATE")
            .setDefaultTime(timestampMillis)
            .setOnChoose("确定") {millisecond->
                print("millisecond：$millisecond")
                date2 = simpleDateFormat.format(Date(millisecond))
                selectedDateTextView2.text = date2
            }.build().show()
    }
}