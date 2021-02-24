package kr.nutee.nutee_android.network

import kr.nutee.nutee_android.data.ResponseWrapper
import retrofit2.Call
import retrofit2.http.GET

interface SNSCategoryAPI {
    @GET("/sns/category/interests")
    fun getCategory(): Call<ResponseWrapper<List<String>>>

    @GET("/sns/category/majors")
    fun getMajors(): Call<ResponseWrapper<List<String>>>
}
