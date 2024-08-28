package com.example.test.view

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

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.page)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val userNameEditText: EditText = findViewById(R.id.username)
        val passwordEditText: EditText = findViewById(R.id.password)
        bindClearInput(userNameEditText)
        bindEyeIconChange()
        bindLogin()
    }
    // 用户名清空方法
    private final fun bindClearInput(userNameEditText: EditText) {
        val clearUserNameIcon: ImageView = findViewById(R.id.clearUserNameIcon)
        clearUserNameIcon.setOnClickListener {
            userNameEditText.setText("")
        }
    }
    // 密码显隐方法
    private final fun bindEyeIconChange() {

    }
    // 登录
    private final fun bindLogin() {
        // 使用DataBinding设置布局
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 设置登录按钮点击事件
        binding.loginButton.setOnClickListener {
            val username = binding.username.text.toString()
            val password = binding.password.text.toString()
            loginViewModel.login(username, password)
        }

        // 观察登录结果并更新UI
        loginViewModel.loginResult.observe(this, Observer { result ->
            if (result.success) {
                Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, result.errorMessage ?: "Login failed", Toast.LENGTH_SHORT).show()
            }
        })
    }
}