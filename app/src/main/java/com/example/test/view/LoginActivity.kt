package com.example.test.view

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.test.R
import androidx.lifecycle.Observer
import com.example.test.databinding.ActivityLoginBinding
import com.example.test.viewmodel.LoginViewModel
import android.widget.Toast
import androidx.activity.viewModels
import com.example.test.viewmodel.CodeViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.test.utils.ViewModelFactory
import com.example.test.data.repository.CodeRepository
import com.example.test.network.RetrofitClient
import com.example.test.data.api.ApiService
import com.example.test.utils.Result
import com.example.test.data.model.CodeResponseData
import android.graphics.BitmapFactory
import android.text.InputType
import android.util.Base64

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var codeViewModel: CodeViewModel
    private lateinit var codeImage: ImageView
    private val apiService = RetrofitClient.getCommonService()
    private var verKey: String = ""
    private var isPasswordVisible: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.page)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding = ActivityLoginBinding.inflate(layoutInflater)
        // 使用DataBinding设置布局
        setContentView(binding.root)
        codeImage = findViewById(R.id.codePic)
        getCode()
        bindClearInput()
        bindEyeIconChange()
        bindCodeClick()
        bindLogin()
    }
    // 获取验证码
    private final fun getCode() {
        val repository = CodeRepository(apiService)
        codeViewModel = ViewModelProvider(this, ViewModelFactory<CodeRepository>(repository)).get(CodeViewModel::class.java)
        codeViewModel.code.observe(this, Observer { result ->
            when (result) {
                is Result.Success -> {
                    verKey = result.data.ver_key;
                    // 显示数据
                    updateImage(result.data)
                }
                is Result.Error -> {
                    // 显示错误信息
                }
            }
        })
        codeViewModel.fetchCode()
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
    // 用户名清空方法
    private final fun bindClearInput() {
        val userNameEditText: EditText = findViewById(R.id.username)

        binding.clearUserNameIcon.setOnClickListener {
            println("点中了")
            userNameEditText.setText("")
        }
    }
    // 密码显隐方法
    private final fun bindEyeIconChange() {
        val passText: EditText = findViewById(R.id.password)
        val icon: ImageView = findViewById(R.id.eyeIcon)
        icon.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            if (isPasswordVisible) {
                // 显示密码
                passText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                icon.setImageResource(R.drawable.ic_open_eye)
            } else {
                // 隐藏密码
                passText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                icon.setImageResource(R.drawable.ic_close_eye)
            }
            passText.setSelection(passText.text.length)
        }
    }
    // 绑定验证码点击
    fun bindCodeClick() {
        codeImage.setOnClickListener {
            getCode()
        }
    }
    // 登录
    private final fun bindLogin() {
        // 设置登录按钮点击事件
        binding.loginButton.setOnClickListener {
            val username = binding.username.text.toString()
            val password = binding.password.text.toString()
            val verCode = binding.verCode.text.toString()
            loginViewModel.login(username, password, verKey, verCode, "password")
        }

        // 观察登录结果并更新UI
        loginViewModel.loginResponse.observe(this, Observer { result ->
            if (result.success) {
                Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, RepairActivity::class.java)
                intent.let { startActivity(it) }
            } else {
                Toast.makeText(this, result.errorMessage ?: "Login failed", Toast.LENGTH_SHORT).show()
            }
        })
    }
}