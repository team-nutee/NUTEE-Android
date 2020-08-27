package kr.nutee.nutee_android.network

import kr.nutee.nutee_android.data.main.home.Notice
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header


interface NoticeRequestInterface {

	@GET("haksa")
	fun requestBachelor(
		@Header("Content-Type") contentType: String = "application/x-www-form-urlencoded"
	) : Call<Notice>

	@GET("sooup")
	fun requestClass(
		@Header("Content-Type") contentType: String = "application/x-www-form-urlencoded"
	) : Call<Notice>

	@GET("hakjum")
	fun requestExchange(
		@Header("Content-Type") contentType: String = "application/x-www-form-urlencoded"
	) : Call<Notice>

	@GET("janghak")
	fun requestScholarship(
		@Header("Content-Type") contentType: String = "application/x-www-form-urlencoded"
	) : Call<Notice>

	@GET("ilban")
	fun requestGeneral(
		@Header("Content-Type") contentType: String = "application/x-www-form-urlencoded"
	) : Call<Notice>

	@GET("hangsa")
	fun requestEvent(
		@Header("Content-Type") contentType: String = "application/x-www-form-urlencoded"
	) : Call<Notice>

}