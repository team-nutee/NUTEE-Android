package kr.nutee.nutee_android.network

import kr.nutee.nutee_android.data.App
import kr.nutee.nutee_android.data.main.RequestReport
import kr.nutee.nutee_android.data.main.add.RequestFixPost
import kr.nutee.nutee_android.data.main.add.RequestPost
import kr.nutee.nutee_android.data.main.home.Comment
import kr.nutee.nutee_android.data.main.home.ResponseMain
import kr.nutee.nutee_android.data.main.home.ResponseMainItem
import kr.nutee.nutee_android.data.main.home.detail.RequestComment
import kr.nutee.nutee_android.data.member.login.RequestLogin
import kr.nutee.nutee_android.data.member.login.ResponseLogin
import kr.nutee.nutee_android.data.member.logout.ResponseLogout
import kr.nutee.nutee_android.data.member.register.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface RequestInterface {

	//Main loading
	@GET("/api/posts")
	fun requestMain(
		@Query("lastId") lastId: Int,
		@Query("limit") limit: Int
	): Call<ResponseMain>

	//comment
	@POST("/api/post/{id}/comment")
	fun requestComment(
		@Header("Cookie") cookie: String,
		@Path("id") id: Int,
		@Body content: RequestComment
	): Call<Comment>

	//Post
	@POST("/api/post")
	fun requestPost(
		@Header("Cookie") cookie: String,
		@Body content: RequestPost
	): Call<ResponseMainItem>

	@Multipart
	@POST("/api/post/images")
	fun requestImage(@Part image: ArrayList<MultipartBody.Part>): Call<ArrayList<String>>

	// delete post
	@DELETE("/api/post/{id}")
	fun requestDelete(@Header("Cookie") cookie: String, @Path("id") id: Int?): Call<Unit>

	// report post
	@POST("/api/post/{id}/report")
	fun requestReport(@Body content: RequestReport, @Path("id") id: Int?): Call<Unit>

	//Like post
	@POST("/api/post/{id}/like")
	fun requestLike(@Header("Cookie") cookie: String, @Path("id") id: Int?): Call<Unit>

	@DELETE("/api/post/{id}/like")
	fun requestDelLike(@Header("Cookie") cookie: String, @Path("id") id: Int?): Call<Unit>

	@PATCH("/api/post")
	fun requestFixPost(
		@Header("Cookie") cookie: String,
		@Body content: RequestFixPost
	): Call<ResponseMainItem>

	/*User Profile*/
	// load user
	@GET("/api/user")
	fun requestUserData(@Header("Cookie") cookie: String): Call<ResponseLogin>

	/*Login*/
	// Login
	@POST("/api/user/login")
	fun requestLogin(@Body body: RequestLogin): Call<ResponseLogin>

	/*Register*/
	//Email OTP
	@POST("/api/user/otpsend")
	fun requestEmailOTP(@Body body: RequestEmailOTP): Call<Unit>

	//check OTP
	@POST("/api/user/otpcheck")
	fun requestOTPCheck(@Body body: RequestOTPCheck): Call<Unit>

	// id Check
	@POST("/api/user/idcheck")
	fun requestIdCheck(@Body body: RequestIdCheck): Call<Unit>

	//nick check
	@POST("/api/user/nicknamecheck")
	fun requestNickCheck(@Body body: RequestNickCheck): Call<Unit>

	//Register
	@POST("/api/user")
	fun requestRegister(@Body body: RequestRegister): Call<ResponseRegister>

	//pw check
	@FormUrlEncoded
	@POST("/api/user/passwordcheck")
	fun requestPasswordCheck(
		@Header("Cookie") cookie: String,
		@Field("password") password:String
	): Call<Unit>

	//password change
	@FormUrlEncoded
	@POST("/api/user/passwordchange")
	fun requestChagePassword(@Header("Cookie") cookie: String,@Field("newpassword")newpassword:String):Call<Unit>

	//logout
	@POST("/api/user/logout")
	fun reqeustLogout(@Header("Cookie") token: String = App.prefs.local_login_token): Call<ResponseLogout>

	/*Find user Data*/
	@FormUrlEncoded
	@POST("/api/user/findid")
	fun requestFindId(@Field("schoolEmail")schoolEmail:String):Call<Unit>

	@FormUrlEncoded
	@POST("/api/user/reissuance")
	fun requestFindPw(@Field("userId")userId:String,@Field("schoolEmail")schoolEmail: String):Call<Unit>


}