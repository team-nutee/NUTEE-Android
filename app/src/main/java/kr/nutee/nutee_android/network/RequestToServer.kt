package kr.nutee.nutee_android.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RequestToServer {
    private val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()
//        .addInterceptor { chain: Interceptor.Chain ->
//            val original = chain.request()
//            if (original.url.encodedPath.equals("/sns/upload", true)
//            ) {
//                chain.proceed(original)
//            } else {
//                chain.proceed(original.newBuilder().apply {
//                    addHeader("Content-Type", "application/json;charset=UTF-8")
//                    logging
//                }.build())
//            }
//        }.build()

    var authRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://3.34.61.71:9708")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    var BackRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://3.34.61.71:9425")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    var noticeRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://nutee.kr:9709/crawl/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    var authService: RequestInterface = authRetrofit.create(RequestInterface::class.java)
    var backService: RequestInterface = BackRetrofit.create(RequestInterface::class.java)
    var noticeService: NoticeRequestInterface = noticeRetrofit.create(NoticeRequestInterface::class.java)
}
