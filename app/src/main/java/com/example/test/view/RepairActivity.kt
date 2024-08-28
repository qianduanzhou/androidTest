package com.example.test.view

import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.test.R
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.graphics.drawable.BitmapDrawable
import android.view.ViewTreeObserver
import androidx.constraintlayout.widget.ConstraintLayout
import android.graphics.*
import android.widget.LinearLayout
import android.view.Gravity
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.children
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.test.data.model.Record
import com.example.test.data.repository.RepairListRepository
import com.example.test.databinding.ActivityRepairBinding
import com.example.test.network.RetrofitClient
import com.example.test.utils.Result
import com.example.test.utils.dpToPx
import com.example.test.view.custom.RepairAdapter
import com.example.test.viewmodel.LoadingViewModel
import com.example.test.viewmodel.LoadingDoneViewModel
import com.example.test.viewmodel.RepairListViewModel

data class Tab(
    val id: String,
    val title: String,
    val value: String,
    val count: Int
)


class RepairActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRepairBinding
    private lateinit var repairListViewModel: RepairListViewModel
    private val loadingViewModel: LoadingViewModel by viewModels()
    private val loadingDoneViewModel: LoadingDoneViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var repairRefreshLayout: SwipeRefreshLayout
    private lateinit var adapter: RepairAdapter
    private val apiService = RetrofitClient.getCommonService()

    private var status = "" // 当前状态
    private var name = "" // 搜索字段
    private var page = 1 // 当前页码
    private var rows = 10 // 每页数量
    private var total = 0 // 总数量

    private var list: MutableList<Record> = mutableListOf() // 列表
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_repair)

        // 数据绑定
        binding = ActivityRepairBinding.inflate(layoutInflater)

        setPageBg()
        setToolBar()
        setTabs()
        setRefresh()
        watchListResult()
        watchLoading()
        watchLoadingDone()
        getList(true)
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.my_menu, menu)
        return true
    }
    // 设置页面背景
    private fun setPageBg() {
        // 获取ConstraintLayout实例
        val constraintLayout = findViewById<ConstraintLayout>(R.id.page)
        constraintLayout.viewTreeObserver.addOnGlobalLayoutListener { object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                // 移除监听器，避免重复触发
                constraintLayout.viewTreeObserver.removeOnGlobalLayoutListener(this)

                // 定义颜色
                val startColor = Color.parseColor("#CBDCFF")
                val endColor = Color.parseColor("#FBFCFF")

                // 计算颜色位置的百分比
                val startFraction = 0f / 100 // 转换为0到1之间的浮点数
                val endFraction = 18f / 100

                // 创建渐变颜色数组和位置数组
                val gradientColors = intArrayOf(startColor, endColor)
                val gradientPositions = floatArrayOf(startFraction, endFraction)

                // 创建Shader
                val shader = LinearGradient(
                    constraintLayout.width.toFloat() / 2,
                    0f,
                    constraintLayout.width.toFloat() / 2,
                    constraintLayout.height.toFloat(),
                    gradientColors,
                    gradientPositions,
                    Shader.TileMode.CLAMP
                )

                // 创建Paint对象并设置Shader
                val paint = Paint().apply {
                    this.shader = shader
                }

                // 创建一个Bitmap，并在其上绘制渐变
                val bitmap = Bitmap.createBitmap(
                    constraintLayout.width,
                    constraintLayout.height,
                    Bitmap.Config.ARGB_8888
                )
                val canvas = Canvas(bitmap)
                canvas.drawPaint(paint)

                // 使用Bitmap创建一个Drawable，并设置为ConstraintLayout的背景
                val drawable = BitmapDrawable(resources, bitmap)
                constraintLayout.background = drawable
            }
        }
        }

    }
    // 设置顶部应用栏
    private fun setToolBar() {
        val toolbar: Toolbar = findViewById(R.id.my_toolbar)
        val backIcon: ImageView = findViewById(R.id.backIcon)
        // 设置
        setSupportActionBar(toolbar)
        // 隐藏标题
        supportActionBar?.setDisplayShowTitleEnabled(false)
        // 设置返回上页
        backIcon.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
    // 设置列表标签
    private fun setTabs() {
        val tabContent = findViewById<LinearLayout>(R.id.tabContent)
        val tabs = arrayOf(
            Tab("tabAll", "全部", "", 0),
            Tab("tabUnprocessed", "待处理", "0", 0),
            Tab("tabDealing", "处理中", "1", 0),
            Tab("tabComplete", "已处理", "2", 0),
        )

        tabContent.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                // 移除监听器，避免重复触发
                tabContent.viewTreeObserver.removeOnGlobalLayoutListener(this)

                // 计算每个tab的宽度
                val tabContentWidth = tabContent.width
                val width = (tabContentWidth / tabs.size).toInt()

                // 清空已有的视图
                tabContent.removeAllViews()

                // 添加新的tab
                tabs.forEach {
                    val constraintLayout = createTab(it, width)
                    tabContent.addView(constraintLayout)
                }

                // 设置激活的tab
                setActiveTab()

                // 设置tab的点击事件
                setTabClick()
            }
        })
    }

    // 创建标签
    private fun createTab(it: Tab, width: Int): ConstraintLayout {
        val cur = this
        // tab容器
        val constraintLayout = ConstraintLayout(cur).apply {
            id = View.generateViewId()
            layoutParams = ConstraintLayout.LayoutParams(
                width,
                dpToPx(32f, context).toInt()
            )
        }
        // 标题
        val textView = TextView(cur)
        // 设置 TextView 的布局参数
        val textLayoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.WRAP_CONTENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT,
        ).apply {
            // 设置约束关系
            startToStart = constraintLayout.id
            endToEnd = constraintLayout.id
        }
        // 底部色块
        val view = View(cur)
        val viewLayoutParams = ConstraintLayout.LayoutParams(
            dpToPx(64f, baseContext).toInt(),
            dpToPx(10f, baseContext).toInt()
        ).apply {
            // 设置约束关系
            startToStart = constraintLayout.id
            bottomToBottom = constraintLayout.id
            marginStart = dpToPx(10f, baseContext).toInt()
        }
        val countTextView = TextView(cur)
        val countTextLayoutParams = ConstraintLayout.LayoutParams(
            dpToPx(16f, baseContext).toInt(),
            dpToPx(12f, baseContext).toInt()
        ).apply {
            // 设置约束关系
            endToEnd = constraintLayout.id
            topToTop = constraintLayout.id
        }
        textView.id = View.generateViewId()
        textView.text = it.title
        textView.textSize = 18f
        textView.typeface = Typeface.createFromAsset(assets, "font/dingtalk_jinbuti.ttf")
        textView.setTextColor(getColor(R.color.theme2))

        view.setBackgroundResource(R.drawable.tab_bg)

        countTextView.id = View.generateViewId()
        countTextView.text = it.count.toString()
        countTextView.textSize = 10f
        countTextView.setTextColor(Color.parseColor("#FFFFFF"))
        countTextView.setBackgroundResource(R.drawable.tab_count_bg)
        countTextView.gravity = Gravity.CENTER

        constraintLayout.addView(textView, textLayoutParams)
        constraintLayout.addView(countTextView, countTextLayoutParams)
        constraintLayout.addView(view, viewLayoutParams)
        constraintLayout.tag = it
        return constraintLayout
    }
    // 设置当前标签
    private fun setActiveTab() {
        val tabContent = findViewById<LinearLayout>(R.id.tabContent)
        tabContent.children.forEach {
            val tag = it.tag as Tab
            if (it is ConstraintLayout) {
                val textView = it.getChildAt(0) as? TextView
                var view = it.getChildAt (2) as? View
                if (tag.value == status) {
                    textView?.setTextColor(Color.parseColor("#3988FE")) // 改变文字颜色
                    view?.setBackgroundResource(R.drawable.tab_active_bg)
                } else {
                    textView?.setTextColor(getColor(R.color.theme2)) // 改变文字颜色
                    view?.setBackgroundResource(R.drawable.tab_bg)
                }
            }
        }
    }
    // 设置点击tab事件
    private fun setTabClick() {
        val tabContent = findViewById<LinearLayout>(R.id.tabContent)
        tabContent.children.forEach {
            it.setOnClickListener {
                val tag = it.tag as Tab
                status = tag.value
                setActiveTab()
            }
        }
    }
    // 设置刷新
    private fun setRefresh() {
        // 下拉刷新初始化
        repairRefreshLayout = findViewById(R.id.repairRefreshLayout)
        recyclerView = findViewById(R.id.repairList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        // 下拉刷新
        repairRefreshLayout.setOnRefreshListener {
            page = 1
            loadingDoneViewModel.hide()
            getList(isRefresh = true)
        }
        // 到底加载
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val pastVisibleItems = layoutManager.findFirstVisibleItemPosition()
                if (!loadingViewModel.getLoading() && (visibleItemCount + pastVisibleItems) >= totalItemCount && !loadingDoneViewModel.getLoadingDone()) {
                    // 到达底部，加载更多
                    page ++
                    getList(isRefresh = false)
                }
            }
        })
    }
    // 监听获取列表返回
    private fun watchListResult() {
        // 初始化获取列表viewmodel
        val repairListRepository = RepairListRepository(apiService)
        val repairListViewModelFactory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(RepairListViewModel::class.java)) {
                    return RepairListViewModel(repairListRepository) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
        repairListViewModel = ViewModelProvider(this, repairListViewModelFactory).get(RepairListViewModel::class.java)

        repairListViewModel.repair.observe(this, Observer { result ->
            when (result) {
                is Result.Success -> {
                    val res = result.data
                    val records = res.records
                    total = res.total
                    println("repairres,$res")
                    list.addAll(records)
                    if (list.size >= total) {
                        loadingDoneViewModel.show()
                    }
                    adapter = RepairAdapter(list)
                    recyclerView.adapter = adapter
                    loadingViewModel.hideLoading()
                    repairRefreshLayout.isRefreshing = false
                }
                is Result.Error -> {
                    // 显示错误信息
                    loadingViewModel.hideLoading()
                    repairRefreshLayout.isRefreshing = false
                    Toast.makeText(this, result.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
    // 初始化列表
    private fun getList(isRefresh: Boolean) {
        loadingViewModel.showLoading()
        if (isRefresh) {
            repairRefreshLayout.isRefreshing = true
            list.removeAll(list)
        }
        repairListViewModel.getRepairPageBy(page, rows, status, name)
    }
    // 监听加载
    private fun watchLoading() {
        val loadingView = findViewById<LinearLayout>(R.id.repairLoading)
        loadingViewModel.isLoading.observe(this) { isLoading ->
            if (!repairRefreshLayout.isRefreshing) {
                loadingView.visibility = if (isLoading) View.VISIBLE else View.GONE
            }
        }
    }
    // 监听加载完成
    private fun watchLoadingDone() {
        val loadingDoneView = findViewById<ConstraintLayout>(R.id.repairLoadingDone)
        loadingDoneViewModel.isLoadingDone.observe(this) { isLoadingDone ->
            loadingDoneView.visibility = if (isLoadingDone) View.VISIBLE else View.GONE
        }
    }
}