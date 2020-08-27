package kr.nutee.nutee_android.network

import androidx.media.AudioAttributesCompat
import kr.nutee.nutee_android.data.main.home.Notice
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header


interface NoticeRequestInterface {

	@GET("haksa")
	fun requestBachelor(
<<<<<<< HEAD
		@Header("Contnet-Type") contentType: String = "application/x-www-form-urlencoded"
=======
		@Header("Content-Type") contentType: String = "application/x-www-form-urlencoded"
>>>>>>> 3f19a983bf72abd2896286355eb126cc6c2292a8
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