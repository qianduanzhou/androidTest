package com.example.test

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class page1Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_page1)
        handleBtn()
    }
    fun handleBtn() {
        val btn: Button = findViewById(R.id.btn)
        val left = btn.left
        val right = btn.right

        Log.d("left", left.toString())
    }
}