package kr.nutee.nutee_android.network

import kr.nutee.nutee_android.data.ResponseWrapper
import kr.nutee.nutee_android.data.main.RequestReport
import kr.nutee.nutee_android.data.main.add.ResponseImage
import kr.nutee.nutee_android.data.main.add.RequestRewritePost
import kr.nutee.nutee_android.data.main.add.RequestPost
import kr.nutee.nutee_android.data.main.home.*
import kr.nutee.nutee_android.data.main.home.detail.RequestComment
import kr.nutee.nutee_android.data.main.home.detail.CommentDetail
import kr.nutee.nutee_android.data.main.profile.ResponseProfile
import kr.nutee.nutee_android.data.main.setting.*
import kr.nutee.nutee_android.data.member.find.RequestFindPw
import kr.nutee.nutee_android.data.member.login.RequestLogin
import kr.nutee.nutee_android.data.member.login.ResponseLogin
import kr.nutee.nutee_android.data.member.register.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.Body

/*
* Auth API를 사용하는 메소드의 인터페이스
*
* 로그인, 회원가입, 로그아웃, 아이디/비밀번호 찾기, 설정
* */
interface RequestAuthInterface {

	/*Login*/
	// Login
	@POST("/auth/login")
	fun requestLogin(@Body body: RequestLogin): Call<ResponseWrapper<ResponseLogin>>

	/*Register*/
	//Email OTP
	@POST("/auth/otp")
	fun requestEmailOTP(@Body body: RequestEmail): Call<Unit>

	//check OTP
	@POST("/auth/check/otp")
	fun requestOTPCheck(@Body body: RequestOTPCheck): Call<Unit>

	// id Check
	@POST("/auth/check/user-id")
	fun requestIdCheck(@Body body: RequestIdCheck): Call<Unit>

	//nick check
	@POST("/auth/check/nickname")
	fun requestNickCheck(@Body body: RequestNickCheck): Call<Unit>

	//Register
	@POST("/auth/user")
	fun requestRegister(@Body body: RequestRegister): Call<ResponseRegister>

	//pw check
	@FormUrlEncoded
	@POST("/api/user/passwordcheck")
	fun requestPasswordCheck(
		@Header("Cookie") cookie: String,
		@Field("password") password: String
	): Call<Unit>

	//logout
	@POST("/auth/logout")
	fun reqeustLogout(
			@Body body:String
	): Call<ResponseWrapper<String>>

	/*Find user Data*/
	@POST("/auth/user-id")
	fun requestFindId(
			@Body body: RequestEmail
	): Call<ResponseWrapper<String>>

	@PATCH("/auth/password")
	fun requestFindPw(
		@Body body:RequestFindPw
	): Call<ResponseWrapper<String>>

	/*Setting*/
	//profile image setting
	@PATCH("/auth/user/profile")
	fun requestToUploadProfile(
			@Header("Authorization") Authorization:String,
			@Body body:RequestChangeProfileImage
	):Call<ResponseWrapper<String>>

	//nickName change
	@PATCH("/auth/user/nickname")
	fun requestToNickNameChange(
			@Header("Authorization") Authorization:String,
		@Body body:RequestChangeNickname
	):Call<ResponseWrapper<String>>


	//password change
	@PATCH("/auth/user/password")
	fun requestChagePassword(
			@Header("Authorization") Authorization:String,
			@Body body:RequestChangePassword
	): Call<ResponseWrapper<String?>>

	//interests change
	@PATCH("/auth/user/interests")
	fun requestChageCategory(
			@Header("Authorization") Authorization:String,
			@Body body:RequestChangeCategory
	):Call<ResponseWrapper<List<String>>>

	//majors change
	@PATCH("/auth/user/majors")
	fun requestChangeMajors(
			@Header("Authorization") Authorization:String,
			@Body body:RequestChangeMajors
	):Call<ResponseWrapper<List<String>>>
}