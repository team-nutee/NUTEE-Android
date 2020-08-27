package kr.nutee.nutee_android.network

import kr.nutee.nutee_android.data.main.home.Notice
import retrofit2.Call
import retrofit2.http.GET


interface NoticeRequestInterface {

	@GET("haksa")
	fun requestBachelor() : Call<Notice>

	@GET("sooup")
	fun requestClass() : Call<Notice>

	@GET("hakjum")
	fun requestExchange() : Call<Notice>

	@GET("janghak")
	fun requestScholarship() : Call<Notice>

	@GET("ilban")
	fun requestGeneral() : Call<Notice>

	@GET("hangsa")
	fun requestEvent() : Call<Notice>

}