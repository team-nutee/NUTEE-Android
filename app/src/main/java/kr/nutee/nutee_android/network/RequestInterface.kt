package kr.nutee.nutee_android.network

import kr.nutee.nutee_android.data.App
import kr.nutee.nutee_android.data.main.RequestReport
import kr.nutee.nutee_android.data.main.add.RequestFixPost
import kr.nutee.nutee_android.data.main.add.RequestPost
import kr.nutee.nutee_android.data.main.home.*
import kr.nutee.nutee_android.data.main.home.detail.RequestComment
import kr.nutee.nutee_android.data.main.profile.ResponseProfile
import kr.nutee.nutee_android.data.main.setting.ResponseUploadProfile
import kr.nutee.nutee_android.data.member.login.RequestLogin
import kr.nutee.nutee_android.data.member.login.ResponseLogin
import kr.nutee.nutee_android.data.member.logout.ResponseLogout
import kr.nutee.nutee_android.data.member.register.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.Body

interface RequestInterface {

	/*list*/
	//LookUp category list
	@GET("/sns/post/category/{INTER2}")
	fun requestCategoryList(
		@Header("Authorization") Authorization:String,
		@Path("INTER2") INTER2:String?,
		@Query("lastId") lastId: Int,
		@Query("limit") limit: Int
	): Call<LookUpList>

	//LookUp favorite list
//	@Headers(
//		"Accept:application/hal+json",
//		"Content-Type:application/json;charset=UTF-8")
	@GET("/sns/post/favorite")
	fun requestFavoriteList(
		@Query("lastId") lastId: Int,
		@Query("limit") limit: Int
	):Call<LookUpList>

	//LookUp full list
	@GET("/sns/post")
	fun requestFullList(
		@Query("lastId") lastId: Int,
		@Query("limit") limit: Int
	):Call<LookUpList>

	/*post*/
	//LookUp post detail
	@GET("/sns/post/{id}")
	fun requestDetail(
		@Path("id") id: Int
	): Call<LookUpDetail?>

	//Posting
	@POST("/sns/post")
	fun requestPost(
		@Header("Cookie") cookie: String,
		@Body content: RequestPost
	): Call<LookUpDetail>

	//rewrite post
	@PATCH("/sns/post/{id}")
	fun requestFixPost(
		@Header("Cookie") cookie: String,
		@Path("id") id:Number?,
		@Body content: RequestFixPost
	): Call<LookUpDetail>

	// delete post
	@DELETE("/sns/post/{id}")
	fun requestDelete(
		@Header("Cookie") cookie: String,
		@Path("id") id: Number?
	): Call<LookUpDetail>

	// report post
	@POST("/sns/post/{id}/report")
	fun requestReport(
		@Body content: RequestReport,
		@Path("id") id: Number?
	): Call<LookUpDetail>

	/*comment*/
	//LookUp comments list
	@GET("/sns/post/{id}/comments")
	fun requestCommentList(
		@Path("id") id:Int
	):Call<Comment>

	//post comment
	@POST("/api/post/{id}/comment")
	fun requestComment(
		@Header("Cookie") cookie: String,
		@Path("id") id: Int,
		@Body content: RequestComment
	): Call<Comment>


	@Multipart
	@POST("/api/post/images")
	fun requestImage(@Part image: ArrayList<MultipartBody.Part>): Call<ArrayList<String>>

	//Like post
	@POST("/api/post/{id}/like")
	fun requestLike(@Header("Cookie") cookie: String, @Path("id") id: Number?): Call<Unit>

	//UnLike post
	@DELETE("/api/post/{id}/like")
	fun requestDelLike(@Header("Cookie") cookie: String, @Path("id") id: Number?): Call<Unit>


	/*comment*/
	//comment Del
	@DELETE("/api/post/{postId}/comment/{id}")
	fun requestDelComment(@Path("postId") postId: Int?, @Path("id") id: Int?): Call<Unit>

	/*User Profile*/
	// load user
	@GET("/api/user")
	fun requestUserData(@Header("Cookie") cookie: String): Call<ResponseProfile>

	//load user posts
	@GET("/api/user/{id}/posts")
	fun requestUserPosts(@Path("id") id: Int): Call<ResponseMain>

	/*Login*/
	// Login
	@POST("/auth/login")
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
		@Field("password") password: String
	): Call<Unit>

	//logout
	@POST("/api/user/logout")
	fun reqeustLogout(@Header("Cookie") token: String = App.prefs.local_login_token): Call<ResponseLogout>

	/*Find user Data*/
	@FormUrlEncoded
	@POST("/api/user/findid")
	fun requestFindId(@Field("schoolEmail") schoolEmail: String): Call<Unit>

	@FormUrlEncoded
	@POST("/api/user/reissuance")
	fun requestFindPw(
		@Field("userId") userId: String,
		@Field("schoolEmail") schoolEmail: String
	): Call<Unit>

	/*Search*/
	//search
	@GET("/api/search/{text}")
	fun requestSearch(
		@Path("text") text: String?,
		@Query("lastId") lastId: Int,
		@Query("limit") limit: Int
	): Call<LookUpList>

	/*Setting*/
	//profile setting
	@Multipart
	@POST("/api/user/profile")
	fun requestToUploadProfile(
		@Header("Cookie") token: String,
		@Part src:MultipartBody.Part
	):Call<ResponseUploadProfile>

	//nickName change
	@FormUrlEncoded
	@PATCH("/api/user/nickname")
	fun requestToNickNameChange(
		@Header("Cookie") token: String,
		@Field("nickname") nickname:String
	):Call<Unit>


	//password change
	@FormUrlEncoded
	@POST("/api/user/passwordchange")
	fun requestChagePassword(
		@Header("Cookie") cookie: String,
		@Field("newpassword") newpassword: String
	): Call<Unit>

	/*other User Profile*/
	// load user
	@GET("/api/user/{id}")
	fun requestUserProfile(
		@Path("id") id: Int
	): Call<ResponseProfile>


}