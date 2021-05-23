package kr.nutee.nutee_android.network

import kr.nutee.nutee_android.data.ResponseWrapper
import kr.nutee.nutee_android.data.main.home.NoticeItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header


interface NoticeRequestInterface {

	@GET("haksa")
	fun requestBachelor(
		@Header("Content-Type") contentType: String ="application/json;charset=UTF-8"
	) : Call<ResponseWrapper<Array<NoticeItem>>>

	@GET("sooup")
	fun requestClass(
		@Header("Content-Type") contentType: String = "application/json;charset=UTF-8"
	) : Call<ResponseWrapper<Array<NoticeItem>>>

	@GET("hakjum")
	fun requestExchange(
		@Header("Content-Type") contentType: String = "application/json;charset=UTF-8"
	) : Call<ResponseWrapper<Array<NoticeItem>>>

	@GET("janghak")
	fun requestScholarship(
		@Header("Content-Type") contentType: String = "application/json;charset=UTF-8"
	) : Call<ResponseWrapper<Array<NoticeItem>>>

	@GET("ilban")
	fun requestGeneral(
		@Header("Content-Type") contentType: String = "application/json;charset=UTF-8"
	) : Call<ResponseWrapper<Array<NoticeItem>>>

	@GET("hangsa")
	fun requestEvent(
		@Header("Content-Type") contentType: String = "application/json;charset=UTF-8"
	) : Call<ResponseWrapper<Array<NoticeItem>>>

}