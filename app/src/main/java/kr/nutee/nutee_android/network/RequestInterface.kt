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
import kr.nutee.nutee_android.data.main.setting.RequestChangeCategory
import kr.nutee.nutee_android.data.main.setting.RequestChangeNickname
import kr.nutee.nutee_android.data.main.setting.ResponseUploadProfile
import kr.nutee.nutee_android.data.member.find.RequestFindPw
import kr.nutee.nutee_android.data.member.login.RequestLogin
import kr.nutee.nutee_android.data.member.login.ResponseLogin
import kr.nutee.nutee_android.data.member.register.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.Body

interface RequestInterface {

	/*list*/
	//LookUp category list
	@GET("/sns/post/category/{category}")
	fun requestCategoryList(
		@Header("Authorization") Authorization:String,
		@Path("category") category:String?,
		@Query("lastId") lastId: Int,
		@Query("limit") limit: Int
	): Call<LookUpList>

	//LookUp favorite list
	@GET("/sns/post/favorite")
	fun requestFavoriteList(
		@Header("Authorization") Authorization:String,
		@Query("lastId") lastId: Int,
		@Query("limit") limit: Int
	):Call<LookUpList>

	//LookUp full list
	@GET("/sns/post/all")
	fun requestFullList(
			@Header("Authorization") Authorization:String,
			@Query("lastId") lastId: Int,
			@Query("limit") limit: Int
	):Call<LookUpList>

	//LookUp My Major list
	@GET("/sns/post/major")
	fun requestMyMajorList(
			@Header("Authorization") Authorization:String,
			@Query("lastId") lastId: Int,
			@Query("limit") limit: Int
	):Call<LookUpList>

	/*post*/
	//LookUp post detail
	@GET("/sns/post/{id}")
	fun requestDetail(
		@Header("Authorization") Authorization:String,
		@Path("id") id:Int
	): Call<LookUpDetail?>

	//Posting
	@POST("/sns/post")
	fun requestPost(
		@Header("Authorization") Authorization:String,
		@Body content: RequestPost
	): Call<LookUpDetail>

	//upload Image
	@Multipart
	@POST("/sns/upload")
	fun requestUploadImage(
		@Part images: ArrayList<MultipartBody.Part>
	):Call<ResponseImage>

	//rewrite post
	@PATCH("/sns/post/{id}")
	fun requestRewritePost(
		@Header("Authorization") Authorization:String,
		@Body content: RequestRewritePost,
		@Path("id") id: Int?
	): Call<LookUpDetail>

	// delete post
	@DELETE("/sns/post/{id}")
	fun requestDelete(
		@Header("Authorization") Authorization:String,
		@Path("id") id: Int?
	): Call<LookUpDetail>

	// report post
	@POST("/sns/post/{id}/report")
	fun requestReport(
		@Body content: RequestReport,
		@Path("id") id: Int?
	): Call<LookUpDetail>


	//Like post
	@POST("/sns/post/{id}/like")
	fun requestLike(
		@Header("Authorization") Authorization:String,
		@Path("id") id: Int?
	): Call<LookUpDetail>

	//UnLike post
	@DELETE("/sns/post/{id}/unlike")
	fun requestDelLike(
		@Header("Authorization") Authorization:String,
		@Path("id") id: Int?
	): Call<LookUpDetail>

	/*comment*/
	//LookUp comments list
	@GET("/sns/post/{id}/comments")
	fun requestCommentList(
		@Header("Authorization") Authorization:String,
		@Path("id") id:Int?,
		@Query("lastId") lastId: Int,
		@Query("limit") limit: Int
	):Call<CommentList?>

	//post comment
	@POST("/sns/post/{id}/comment")
	fun requestComment(
		@Header("Authorization") Authorization:String,
		@Path("id") id: Int?,
		@Body content: RequestComment
	): Call<CommentDetail>

	//Del comment
	@DELETE("/sns/post/{postId}/comment/{id}")
	fun requestDelComment(
		@Header("Authorization") Authorization:String,
		@Path("postId") postId: Int?,
		@Path("id") id: Int?
	): Call<CommentList?>

	//rewrite comment
	@PATCH("/sns/post/{postId}/comment/{id}")
	fun requestRewriteComment(
		@Header("Authorization") Authorization:String,
		@Path("postId") postId: Int?,
		@Path("id") id: Int?,
		@Body content: RequestComment
	): Call<CommentDetail?>

	//report comment
	@POST("/sns/post/{postId}/comment/{id}/report")
	fun requestReportComment(
		@Header("Authorization") Authorization:String,
		@Path("postId") postId: Int?,
		@Path("id") id: Int?,
		@Body content: RequestReport
	): Call<CommentDetail?>

	//post reply
	@POST("/sns/post/{postId}/comment/{id}")
	fun requestReply(
		@Header("Authorization") Authorization:String,
		@Path("postId") postId: Int?,
		@Path("id") id: Int?,
		@Body content: RequestComment
	):Call<CommentDetail>

	//like comment
	@POST("/sns/post/{postId}/comment/{id}/like")
	fun requestLikecomment(
		@Header("Authorization") Authorization:String,
		@Path("postId") postId: Int?,
		@Path("id") id: Int?
	):Call<CommentDetail>

	//ulike comment
	@DELETE("/sns/post/{postId}/comment/{id}/unlike")
	fun requestDelLikecomment(
		@Header("Authorization") Authorization:String,
		@Path("postId") postId: Int?,
		@Path("id") id: Int?
	):Call<CommentDetail>

	/*User Profile*/
	// load user
	@GET("/sns/user/me")
	fun requestUserData(
			@Header("Authorization") Authorization:String
	): Call<ResponseProfile>

	@GET("/sns/user/me/posts")
	fun requestMyPosts(
			@Header("Authorization") Authorization:String,
			@Query("lastId") lastId: Int,
			@Query("limit") limit: Int
	):Call<ResponseWrapper<Array<ResponseMainBody>>>

	@GET("/sns/user/me/comment/posts")
	fun requestMyCommentPosts(
			@Header("Authorization") Authorization:String,
			@Query("lastId") lastId: Int,
			@Query("limit") limit: Int
	):Call<ResponseWrapper<Array<ResponseMainBody>>>

	@GET("/sns/user/me/like/posts")
	fun requestMyLikePosts(
			@Header("Authorization") Authorization:String,
			@Query("lastId") lastId: Int,
			@Query("limit") limit: Int
	):Call<ResponseWrapper<Array<ResponseMainBody>>>

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

	/*Search*/
	//search
	@GET("/sns/search/{text}")
	fun requestSearch(
		@Header("Authorization") Authorization:String,
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
	@PATCH("/auth/user/nickname")
	fun requestToNickNameChange(
			@Header("Authorization") Authorization:String,
		@Body body:RequestChangeNickname
	):Call<ResponseWrapper<String>>


	//password change
	@FormUrlEncoded
	@POST("/api/user/passwordchange")
	fun requestChagePassword(
		@Header("Cookie") cookie: String,
		@Field("newpassword") newpassword: String
	): Call<Unit>

	//interests change
	@PATCH("/auth/user/interests")
	fun requestChageCategory(
			@Header("Authorization") Authorization:String,
			@Body body:RequestChangeCategory
	):Call<ResponseWrapper<List<String>>>

	/*other User Profile*/
	// load user
	@GET("/api/user/{id}")
	fun requestUserProfile(
		@Path("id") id: Int
	): Call<ResponseProfile>


	@GET("/sns/category/interests")
	fun getCategory(): Call<ResponseWrapper<List<String>>>

	@GET("/sns/category/majors")
	fun getMajors(): Call<ResponseWrapper<List<String>>>

}