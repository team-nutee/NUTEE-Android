package kr.nutee.nutee_android.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RequestToServer {
    var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://13.124.232.115:9425")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    var service: RequestInterface = retrofit.create(RequestInterface::class.java)
}