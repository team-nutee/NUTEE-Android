package kr.nutee.nutee_android.network

import androidx.media.AudioAttributesCompat
import kr.nutee.nutee_android.data.main.home.Notice
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header


interface NoticeRequestInterface {

	@GET("haksa")
	fun requestBachelor(
		@Header("Contnet-Type") contentType: String = "application/x-www-form-urlencoded"
	) : Call<Notice>

	@GET("sooup")
	fun requestClass(
		@Header("Contnet-Type") contentType: String = "application/x-www-form-urlencoded"
	) : Call<Notice>

	@GET("hakjum")
	fun requestExchange(
		@Header("Contnet-Type") contentType: String = "application/x-www-form-urlencoded"
	) : Call<Notice>

	@GET("janghak")
	fun requestScholarship(
		@Header("Contnet-Type") contentType: String = "application/x-www-form-urlencoded"
	) : Call<Notice>

	@GET("ilban")
	fun requestGeneral(
		@Header("Contnet-Type") contentType: String = "application/x-www-form-urlencoded"
	) : Call<Notice>

	@GET("hangsa")
	fun requestEvent(
		@Header("Contnet-Type") contentType: String = "application/x-www-form-urlencoded"
	) : Call<Notice>

}