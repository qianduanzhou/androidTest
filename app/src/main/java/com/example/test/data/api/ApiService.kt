package com.example.test.data.api

import com.example.test.data.model.ApiResponse
import com.example.test.data.model.CodeResponseData
import com.example.test.data.model.RepairListResponseData
import retrofit2.http.GET
import retrofit2.Response
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {
    // 获取验证码
    @GET("system/login/captcha")
    suspend fun getCode(): ApiResponse<CodeResponseData>

    // 获取维修列表
    @GET("inspection/fix/repairPageBy")
    @Headers("Authorization: Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiI5OWZmNjczMmMxNzA4YTUyMDNmM2U0ZGRmYTJlYzljYiIsInByb2R1Y3RfaWQiOiJjYWE0ODY5MmY5MWQ0NDZiYWJjNGU4N2FkM2NkNDFjYyIsInNjb3BlIjpbImxpbiJdLCJleHAiOjE3MjU5NTE1NTcsImF1dGhvcml0aWVzIjpbIntjYWE0ODY5MmY5MWQ0NDZiYWJjNGU4N2FkM2NkNDFjY31hdWxCRnl3K1ZYRVVodFJzWmVZclpORVV4c294L1hrU2czUE9mRkp4ODJXcVltM3dPcTFqaXJDZlltR1RoNkJYTit5bU8zVSttSXJzbWlmV0xtekczVGxGaVBrN1R6eTk1VXNRbEs5NXVkT0wzRjZwMTFDTk0xUXdKYi8zN3h4M1g2WEpDWFBYNVk3cHVPakxiZjRMNTFNQWFMdXRVQTlIZnl4cUJFczNPalBBcTB1NEJPOWk2UFZOZEZBT0NyUWF5eXhpNDkyZnY0dGVLMTdNVkJnMEdDREx5YUZpWlNWRlE1R01GTWZlc1lkdHVaQ3dmNW9hbWJCeTdFN1JSVHNuQ2NtOEVCOWpJNS9SVUpYdUhtYm5BZmMzZ0E3c282dkk0WkExM1BrcWh3VlVRN005T3JYNWE0a3ozdHNQVzU0TGJYbzlGa01qTFhDN1VjUUNGaFk3TnJjbkFaVXZ6dVpJZXNMSTJQRnN2ZHNyV3dwSUtkUGcvMHBvdk91YkpGOWI5ZHpMeENEQm5RdVVzTnFyUm5TMHlHWStTWjFTYi9zQWRZQWZic09aR3Vha3lxMmwxSWNtRUlQSGd0N0xVRGVnbEJENW5IYTNKYk9UZEtYVUpKSG5uby9Ba093OExldnpqMG8yZXM2bHEwdVQ4eXJFQkh0aFZ4WGlNUk5xNzlUTmNvMENLODd0cURLNDZ0S3h4QVNEN24rdEU1aGsvbEd2UlF0RzBKaGpWc2JwcmhpaWhBK1dLSWM5cm1yWGdXV2hVM1BsSnBoYWxTaTRHMmJXSUJjM3BJWEpxd3BFaXpjSkVxV1NOSlc5S3ZGNVRFSGwxdXJKNTRqS3JDQTJLL1pKVmR4NzlUZzczeFNhZFZZK3JaemIxeWFwTUFJOVBtdzhrRjRKck85SjZvWTljb3RrZnpTYnk3b1FnTHk3c0hoVjhBS1ZHc2ZUb0FQWHdYaGpBb05sbndhSVNLajJrR3hvb1BBVHd6YWFLQ2VSY3FCZkdqaDRUSVoxZkhycTR3RnltbDFCWlAxRllnR0UzV2tzdXhEcmpFdjMzb3E1cnVneUlWaHdlcHdnOFdwTTNxcklpYTBCUzlVNDZzZVRnRVo1Zm9mbUdCM01lZ0VibnVObjcvVjN6R2JnM2x0clhYbThRNFNZRTNhVGpYQjVUT0YxR0lqNjVHamtrRnZEMjJ3ZTRGMUJVTTVZdnhQNGE5bFJqNW14ZU1uaGRaNy8vUFZ5ZnFHNTRkTjJ1ckhHRklzWlE2bXR4cWFMeVRTbFFZYTZoUWo5WlMvV3pPSGQyS0Y3UDdVdklEVXpvaS9XT0ozaGNOYmxka1ZGQ0hxb0F5S2hhaWtMbm9MQlNEQVdkRmZLSkkvcDg3UVcwS3RKZ2ttNi9KMlZLb2t4THJKdkJjMDB1eFpUeXVyOEZYQzI3ZmJOWnBOQXA1ZkZWU3BKK09PNjUxeGgwUDFMZmJGN3AxampKcjRzaG0xZ0tUcGF6T3hteUVCNXJsK3lYOVk9Il0sImp0aSI6Im43Uzhndm9sR013X0Job2xiYXQ1aWQxZGtCZyIsImNsaWVudF9pZCI6ImxpbiJ9.pJZBqmAw1k018LH0spV0txQJl-uOtnmlUtIHipmestgLoRErubIkd7fZunc4WXgqqfc-jzH5qNRvtCAuvVZ7H-NCn4mpOj9kwonmJ1uzeXCxVV2qTMzxLJ-s0n15-zNsE2DmVux-dkAcVz6LhiZXjpxOnbM5ugRTIMNRWt8WWt4")
    suspend fun getRepairPageBy(@Query("page") page: Number, @Query("rows") rows: Number, @Query("status") status: String, @Query("name") name: String): ApiResponse<RepairListResponseData>
}