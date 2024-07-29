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
        var map6 = mapOf("name" to "页面6", "key" to "page6")
        var map7 = mapOf("name" to "页面7", "key" to "page7")
        var map8 = mapOf("name" to "页面8", "key" to "page8")
        var map9 = mapOf("name" to "页面9", "key" to "page9")
        var map10 = mapOf("name" to "页面10", "key" to "page10")

        data = listOf(map1, map2, map3, map4, map5, map6, map7, map8, map9, map10)
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
                "page2" -> Intent(this, page2Activity::class.java)
                "page3" -> Intent(this, page3Activity::class.java)
                "page4" -> Intent(this, page4Activity::class.java)
                "page5" -> Intent(this, page5Activity::class.java)
                "page6" -> Intent(this, page6Activity::class.java)
                "page7" -> Intent(this, page7Activity::class.java)

                else -> null
            }
            intent?.let { startActivity(it) }
        }
    }
}
