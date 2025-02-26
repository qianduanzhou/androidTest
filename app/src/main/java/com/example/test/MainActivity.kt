package com.example.test

import android.os.Bundle
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.test.data.model.CodeResponseData
import com.example.test.data.repository.CodeRepository
import com.example.test.network.RetrofitClient
import com.example.test.utils.ViewModelFactory
import com.example.test.viewmodel.CodeViewModel
import android.util.Base64
import android.graphics.BitmapFactory
import android.view.View
import android.widget.Button
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.test.utils.Result

class MainActivity : AppCompatActivity() {
    private lateinit var codeViewModel: CodeViewModel
    private val apiService = RetrofitClient.getCommonService()
    private lateinit var codeImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // 设置布局
        setContentView(R.layout.activity_main)
        // 获取根视图，并设置 padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // 获取 ImageView
        codeImage = findViewById(R.id.img)
        // 接口请求测试
        apiTest()
    }

    // 接口请求测试
    fun apiTest() {
        // 获取按钮
        val btn = findViewById<Button>(R.id.btn)
        // 创建 CodeRepository
        val repository = CodeRepository(apiService)
        // 创建 CodeViewModel
        codeViewModel = ViewModelProvider(this, ViewModelFactory<CodeRepository>(repository)).get(
            CodeViewModel::class.java)
        // 观察数据
        codeViewModel.code.observe(this, Observer { result ->
            when (result) {
                // 处理成功结果
                is Result.Success -> {
                    if (result.data == null) {
                        codeImage.visibility = View.GONE
                    } else {
                        codeImage.visibility = View.VISIBLE
                        // 显示数据
                        updateImage(result.data)
                    }
                }
                // 处理错误结果
                is Result.Error -> {
                    // 显示错误信息
                }
                else -> {}
            }
        })

        // 点击按钮时触发接口请求
        btn.setOnClickListener {
            codeViewModel.fetchCode()
        }
    }
    // 更新图片
    private fun updateImage(result: CodeResponseData) {
        // 去掉 Base64 前缀（如有）
        val base64Image = result.ver_image.substringAfter("base64,")

        // 将 Base64 字符串解码为字节数组
        val imageBytes = Base64.decode(base64Image, Base64.DEFAULT)

        // 将字节数组解码为 Bitmap
        val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

        // 将 Bitmap 设置为 ImageView 的图片
        codeImage.setImageBitmap(bitmap)
    }
}
