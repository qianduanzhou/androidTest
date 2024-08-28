package com.example.test.view.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Shader
import android.graphics.LinearGradient
import android.util.AttributeSet
import android.view.View
import com.example.test.utils.dpToPx


// 自定义公用背景色
class GradientBackgroundView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val borderWidth = dpToPx(2f, context) // 边框宽度
    private val cornerRadius = dpToPx(16f, context) // 圆角半径
    // 定义颜色
    private val startColor = Color.parseColor("#EAF5FF")
    private val endColor = Color.parseColor("#FFFFFF")
    // 计算颜色位置的百分比
    private val startFraction = 0f / 100 // 转换为0到1之间的浮点数
    private val endFraction = dpToPx(5f, context) / 100
    init {
        // 设置边框的颜色和宽度
        borderPaint.style = Paint.Style.STROKE
        borderPaint.color = endColor
        borderPaint.strokeWidth = borderWidth

        // 初始化渐变背景
        paint.style = Paint.Style.FILL
        val shader = LinearGradient(
            0f, 0f, 0f, 1000f, // 渐变的起始和结束坐标
            intArrayOf(startColor, endColor), // 渐变颜色数组
            floatArrayOf(startFraction, endFraction),
            Shader.TileMode.CLAMP
        )
        paint.shader = shader
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // 创建圆角矩形的区域
        val rectF = RectF(
            borderWidth / 2,
            borderWidth / 2,
            width - borderWidth / 2,
            height - borderWidth / 2
        )

        // 绘制渐变背景
        canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, paint)

        // 绘制边框
        canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, borderPaint)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        // 重新设置渐变的位置和大小
        val shader = LinearGradient(
            0f, 0f, 0f, h.toFloat(), // 从上到下的渐变
            intArrayOf(startColor, endColor), // 渐变颜色数组
            floatArrayOf(startFraction, endFraction),
            Shader.TileMode.CLAMP
        )
        paint.shader = shader
    }
}
