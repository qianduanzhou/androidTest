package com.example.test.view.custom

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.data.model.Record

// 获取故障类型
fun getFaultType(status: String): String? {
    val map = mapOf(
        "0" to "机械故障",
        "1" to "电气故障",
        "2" to "工艺故障",
        "3" to "液体和气动系统故障",
        "4" to "仪表和自动化系统故障",
        "5" to "环境故障",
        "6" to "其他"
    )
    return map[status]
}

// 获取处理类型
fun getFaultOrderStatus(status: String): Map<String, Any>? {
    val map = mapOf(
        "0" to mapOf(
            "text" to "待处理",
            "color" to "#FE4C4D",
            "background" to R.drawable.shape_state_red
        ),
        "1" to mapOf(
            "text" to "处理中",
            "color" to "#FBB143",
            "background" to R.drawable.shape_state_orange
        ),
        "2" to mapOf(
            "text" to "已处理",
            "color" to "#00C55A",
            "background" to R.drawable.shape_state_green
        ),
        "3" to mapOf(
            "text" to "已办结",
            "color" to "#00C55A",
            "background" to R.drawable.shape_state_green
        )
    )
    return map[status]
}

class RepairAdapter(private val dataSet: List<Record>) :
    RecyclerView.Adapter<RepairAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView
        val stateView: TextView

        init {
            textView = view.findViewById(R.id.title)
            stateView = view.findViewById(R.id.state)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.activity_repair_item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val data = dataSet[position]
        val faultTypeStr = getFaultType(data.faultType) as String
        val orderStatusMap = getFaultOrderStatus(data.orderStatus) as Map<String, Any>
        val pointInfo = data.pointInfo
        var managerName = ""
        if (pointInfo != null) {
            managerName = pointInfo.managerName
        }
        viewHolder.textView.text = data.deviceName
        viewHolder.stateView.text = orderStatusMap["text"] as String
        viewHolder.stateView.setBackgroundResource(orderStatusMap["background"] as Int)
        viewHolder.stateView.setTextColor(Color.parseColor(orderStatusMap["color"] as String))
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}
