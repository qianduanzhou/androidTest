package com.example.test

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class animatorActivity : AppCompatActivity() {
    private lateinit var playAnimation: AnimationDrawable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_animator)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // 动画处理
        handleViewB()
        handleViewR()
        handlePlay()
        handleVector()
    }

    // 动画处理
    private fun handleViewB() {
        val viewB = findViewById<View>(R.id.viewB)
        val anim = ObjectAnimator.ofFloat(viewB, "translationX", 0f, 100f).apply {
            duration = 1000
//            start()
        }
        val anim2 = ObjectAnimator.ofFloat(viewB, "translationY", 0f, 100f).apply {
            duration = 1000
        }

        val anim3 = ObjectAnimator.ofFloat(viewB, "translationX", 100f, 0f).apply {
            duration = 1000
        }

        AnimatorSet().apply {
//            // 一起播放
//            play(anim).with(anim2)

//            // 播放anim后播放anim2
//            play(anim).before(anim3)

//            // 顺序播放
            play(anim).before(anim2)
            play(anim3).after(anim2)
            start()
        }
    }

    private fun handleViewR() {
        val viewR = findViewById<View>(R.id.viewR)
        ObjectAnimator.ofFloat(viewR, "alpha", 1f, 0f).apply {
            duration = 1000
            start()
            addListener(object : AnimatorListenerAdapter() {
                // 动画结束
                override fun onAnimationEnd(animation: Animator) {
                    ObjectAnimator.ofFloat(viewR, "alpha", 0f, 1f).apply {
                        duration = 1000
                        start()
                    }
                }
            })
        }
    }
    // 图形动画
    private fun handlePlay() {
        val play = findViewById<ImageView>(R.id.play).apply {
            setBackgroundResource(R.drawable.btn_play)
            playAnimation = background as AnimationDrawable
        }
        play.setOnClickListener {
            playAnimation.start()
        }
    }

    // 矢量动画
    private fun handleVector() {
        val vector = findViewById<ImageView>(R.id.vector);
        val vectorAnimation = vector.drawable as AnimatedVectorDrawable

        vector.setOnClickListener {
            vectorAnimation.start()
        }
    }
}