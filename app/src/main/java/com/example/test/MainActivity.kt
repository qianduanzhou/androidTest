package com.example.test

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.widget.AdapterView
import android.widget.SimpleAdapter
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import android.Manifest
import android.app.PendingIntent
import androidx.core.app.TaskStackBuilder
import com.example.test.view.LoginActivity

class MainActivity : AppCompatActivity() {
    private lateinit var data: List<Map<String, String>>
    private lateinit var builder: NotificationCompat.Builder
    private val CHANNEL_ID = "1"
    private val NOTIFICATION_ID = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        initList()
        handleListView()
        initNotification()
        showNotification()
    }
    private fun initList() {
        var map1 = mapOf("name" to "页面1", "key" to "page1")
        var map2 = mapOf("name" to "页面2", "key" to "page2")
        var map3 = mapOf("name" to "页面3", "key" to "page3")
        var map4 = mapOf("name" to "页面4", "key" to "page4")
        var map5 = mapOf("name" to "页面5", "key" to "page5")
        var map6 = mapOf("name" to "页面6", "key" to "page6")
        var map7 = mapOf("name" to "页面7", "key" to "page7")
        var map8 = mapOf("name" to "datePicker", "key" to "datePicker")
        var map9 = mapOf("name" to "chart", "key" to "chart")
        var map10 = mapOf("name" to "login", "key" to "login")

        data = listOf(map1, map2, map3, map4, map5, map6, map7, map8, map9, map10)
    }
    // 处理列表跳转
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
                "datePicker" -> Intent(this, DatePickerActivity::class.java)
                "chart" -> Intent(this, ChartActivity::class.java)
                "login" -> Intent(this, LoginActivity::class.java)
                else -> null
            }
            intent?.let { startActivity(it) }
        }
    }

    // 初始化通知
    private fun initNotification() {
        // 设置跳转的Activity
        val loginIntent = Intent(this, LoginActivity::class.java)
        // Create the TaskStackBuilder.
        val loginPendingIntent: PendingIntent? = TaskStackBuilder.create(this).run {
            // Add the intent, which inflates the back stack.
            addNextIntentWithParentStack(loginIntent)
            // Get the PendingIntent containing the entire back stack.
            getPendingIntent(0,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        }
        // 创建通知
         builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.cheer)
            .setContentTitle("通知标题")
            .setContentText("通知内容")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT).apply {
                setContentIntent(loginPendingIntent)
            }
    }
    // 显示通知
    private fun showNotification() {
        with(NotificationManagerCompat.from(this)) {
            if (ActivityCompat.checkSelfPermission(
                this@MainActivity,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED) {
                return@with
            }
            notify(NOTIFICATION_ID, builder.build())
        }
    }
}
