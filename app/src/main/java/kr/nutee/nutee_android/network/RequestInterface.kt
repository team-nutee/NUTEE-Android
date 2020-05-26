package kr.nutee.nutee_android.network

import kr.nutee.nutee_android.data.main.home.ResponseMain
import kr.nutee.nutee_android.data.member.login.RequestLogin
import kr.nutee.nutee_android.data.member.login.ResponseLogin
import kr.nutee.nutee_android.data.member.register.*
import retrofit2.Call
import retrofit2.http.*

interface RequestInterface {

	//Main loading
	@GET("/api/posts")
	fun requestMain(@Query("lastId") lastId:Int,
					@Query("limit")limit:Int):Call<ResponseMain>

	// Login
	@POST("/api/user/login")
	fun requestLogin(@Body body: RequestLogin): Call<ResponseLogin>

	/*Register*/
	//Email OTP
	@POST("/api/user/otpsend")
	fun requestEmailOTP(@Body body: RequestEmailOTP): Call<Unit>

	//check OTP
	@POST("/api/user/otpcheck")
	fun requestOTPCheck(@Body body: RequestOTPCheck) : Call<Unit>

	// id Check
	@POST("/api/user/idcheck")
	fun requestIdCheck(@Body body : RequestIdCheck) : Call<Unit>

	//nick check
	@POST("/api/user/nicknamecheck")
	fun requestNickCheck(@Body body: RequestNickCheck): Call<Unit>

	//Register
	@POST("/api/user")
	fun requestRegister(@Body body:RequestRegister): Call<ResponseRegister>

	//pw check
	@POST()
	fun requestPasswordCheck(@Header("Cookie")cookie: String, @Body body : RequestIdCheck): Call<Unit>
}