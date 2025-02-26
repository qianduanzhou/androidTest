package com.example.test

import android.graphics.Color
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

class ChartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_chart)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.chartContainer)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initLineChart()
        initBarChart()
        initPieChart()
    }

    private fun initLineChart() {
        val lineChart = findViewById<LineChart>(R.id.lineChart)
        val entries = ArrayList<Entry>()
        entries.add(Entry(0f, 1f))
        entries.add(Entry(1f, 2f))
        entries.add(Entry(2f, 0f))
        // 创建数据集
        val lineDataSet = LineDataSet(entries, "My Line Chart")
        lineDataSet.color = Color.parseColor("#003366") // 设置线的颜色
        lineDataSet.valueTextColor = Color.parseColor("#D3D3D3") // 设置数据点值的颜色

        // 将数据集绑定到 LineChart
        val lineData = LineData(lineDataSet)
        lineChart.data = lineData

        // 刷新图表
        lineChart.invalidate()
    }

    private fun initBarChart() {
        val barChart = findViewById<BarChart>(R.id.barChart)
        val entries = ArrayList<BarEntry>()
        entries.add(BarEntry(0f, 1f))
        entries.add(BarEntry(1f, 2f))
        entries.add(BarEntry(2f, 0f))
        // 创建数据集
        val barDataSet = BarDataSet(entries, "My Bar Chart")
        barDataSet.color = Color.parseColor("#003366") // 设置线的颜色
        barDataSet.valueTextColor = Color.parseColor("#D3D3D3") // 设置数据点值的颜色

        // 将数据集绑定到 LineChart
        val barData = BarData(barDataSet)
        barChart.data = barData

        // 刷新图表
        barChart.invalidate()
    }

    private fun initPieChart() {
        val pieChart = findViewById<PieChart>(R.id.pieChart)
        val entries = ArrayList<PieEntry>()
        entries.add(PieEntry(0f, 1f))
        entries.add(PieEntry(1f, 2f))
        entries.add(PieEntry(2f, 0f))
        // 创建数据集
        val pieDataSet = PieDataSet(entries, "My Pie Chart")
        pieDataSet.color = Color.parseColor("#003366") // 设置线的颜色
        pieDataSet.valueTextColor = Color.parseColor("#D3D3D3") // 设置数据点值的颜色

        // 将数据集绑定到 LineChart
        val pieData = PieData(pieDataSet)
        pieChart.data = pieData

        // 刷新图表
        pieChart.invalidate()
    }
}