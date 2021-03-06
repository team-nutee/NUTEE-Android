package kr.nutee.nutee_android.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RequestToServer {
    private val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val clientNotice = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    private val clientNutee = OkHttpClient.Builder()
        .addInterceptor { chain: Interceptor.Chain ->
            val request = chain.request()

            val newRequest: Request =
                    if (request.url.encodedPath.equals("/sns/upload", true))
                    {
                        request.newBuilder()
                                .addHeader("Accept","application/hal+json").build()
            } else {
                request.newBuilder()
                        .addHeader("Content-Type", "application/json;charset=UTF-8")
                        .addHeader("Accept","application/hal+json")
                        .build()
            }
            chain.proceed(newRequest)
        }.addInterceptor(logging)
        .build()

    var authRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://3.34.61.71:9708")
        .client(clientNutee)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    var BackRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://3.34.61.71:9425")
        .client(clientNutee)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    var noticeRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://nutee.kr:9709/crawl/")
        .client(clientNotice)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    var authService: RequestInterface = authRetrofit.create(RequestInterface::class.java)
    var backService: RequestInterface = BackRetrofit.create(RequestInterface::class.java)
    var noticeService: NoticeRequestInterface = noticeRetrofit.create(NoticeRequestInterface::class.java)
    //val snsCategoryAPI: SNSCategoryAPI = BackRetrofit.create(SNSCategoryAPI::class.java)
}
