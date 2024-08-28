package com.example.test.data.model

data class PointInfo(
    val managerName: String
)

data class Record(
    val faultType: String,
    val orderStatus: String,
    val reportTime: String,
    val deviceName: String,
    val reportPersonName: String,
    val fixManagerName: String,
    val fixStartTime: String,
    val urgency: String,
    val pointInfo: PointInfo?
)

data class RepairListResponseData(
    val total: Int,
    val records: List<Record>
)
