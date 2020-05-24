package kr.nutee.nutee_android.network

import kr.nutee.nutee_android.data.member.login.RequestLogin
import kr.nutee.nutee_android.data.member.login.ResponseLogin
import kr.nutee.nutee_android.data.member.register.RequestEmailOTP
import kr.nutee.nutee_android.data.member.register.RequestOTPCheck
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RequestInterface {

	// Login
	@POST("/api/user/login")
	fun requestLogin(@Body body: RequestLogin): Call<ResponseLogin>

	// Register

	//Email OTP
	@POST("/api/user/otpsend")
	fun requestEmailOTP(@Body body: RequestEmailOTP): Call<Unit>

	//check OTP
	@POST("/api/user/otpcheck")
	fun requestOTPCheck(@Body body: RequestOTPCheck) : Call<Unit>

	// id Check
}