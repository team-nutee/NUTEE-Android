package kr.nutee.nutee_android.network

import kr.nutee.nutee_android.member.login.RequestLogin
import kr.nutee.nutee_android.member.login.ResponseLogin
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RequestInterface {
	@POST("/api/user/login")
	fun requestLogin(@Body body: RequestLogin) : Call<ResponseLogin>
}