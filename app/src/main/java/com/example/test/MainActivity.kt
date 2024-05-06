package com.example.test

import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.widget.AdapterView
import android.widget.SimpleAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var data: List<Map<String, String>>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        initList()
        handleListView()
    }
    private fun initList() {
        var map1 = mapOf("name" to "页面1", "key" to "page1")
        var map2 = mapOf("name" to "页面2", "key" to "page2")
        var map3 = mapOf("name" to "页面3", "key" to "page3")
        var map4 = mapOf("name" to "页面4", "key" to "page4")
        var map5 = mapOf("name" to "页面5", "key" to "page5")
        data = listOf(map1, map2, map3, map4, map5)
    }
    private fun handleListView() {
        val listView: ListView = findViewById(R.id.list_view)
        val adapter = SimpleAdapter(this, data, R.layout.list_item, arrayOf("name"),
            intArrayOf(R.id.name))
        listView.adapter = adapter
        listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            // Do something in response to the click.
            val item = data[id.toInt()]
            val key = item.get("key")
            var intent = when (key) {
                "page1" -> Intent(this, page1Activity::class.java)
                else -> null
            }
            intent?.let { startActivity(it) }
        }
    }
}
