package kr.nutee.nutee_android.network

import kr.nutee.nutee_android.data.ResponseWrapper
import kr.nutee.nutee_android.data.main.RequestReport
import kr.nutee.nutee_android.data.main.add.RequestPost
import kr.nutee.nutee_android.data.main.add.RequestRewritePost
import kr.nutee.nutee_android.data.main.add.ResponseImage
import kr.nutee.nutee_android.data.main.home.CommentList
import kr.nutee.nutee_android.data.main.home.LookUpDetail
import kr.nutee.nutee_android.data.main.home.LookUpList
import kr.nutee.nutee_android.data.main.home.ResponseMainBody
import kr.nutee.nutee_android.data.main.home.detail.CommentDetail
import kr.nutee.nutee_android.data.main.home.detail.RequestComment
import kr.nutee.nutee_android.data.main.profile.ResponseProfile
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*


/*
* SNS API를 사용하는 메소드의 인터페이스
*
* 게시글 목록 조회, 게시물, 댓글, 내 프로필 조회, 검색, 이미지 업로드, 카테고리 조회
* */
interface RequestSNSInterface {
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
    ): Call<LookUpList>

    //LookUp full list
    @GET("/sns/post/all")
    fun requestFullList(
        @Header("Authorization") Authorization:String,
        @Query("lastId") lastId: Int,
        @Query("limit") limit: Int
    ): Call<LookUpList>

    //LookUp My Major list
    @GET("/sns/post/major")
    fun requestMyMajorList(
        @Header("Authorization") Authorization:String,
        @Query("lastId") lastId: Int,
        @Query("limit") limit: Int
    ): Call<LookUpList>

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
    ): Call<CommentList?>

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

    //comment reply
    @POST("/sns/post/{postId}/comment/{id}")
    fun requestReply(
        @Header("Authorization") Authorization:String,
        @Path("postId") postId: Int?,
        @Path("id") id: Int?,
        @Body content: RequestComment
    ): Call<CommentDetail>

    //like comment
    @POST("/sns/post/{postId}/comment/{id}/like")
    fun requestLikecomment(
        @Header("Authorization") Authorization:String,
        @Path("postId") postId: Int?,
        @Path("id") id: Int?
    ): Call<CommentDetail>

    //ulike comment
    @DELETE("/sns/post/{postId}/comment/{id}/unlike")
    fun requestDelLikecomment(
        @Header("Authorization") Authorization:String,
        @Path("postId") postId: Int?,
        @Path("id") id: Int?
    ): Call<CommentDetail>

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
    ): Call<ResponseWrapper<Array<ResponseMainBody>>>

    @GET("/sns/user/me/comment/posts")
    fun requestMyCommentPosts(
        @Header("Authorization") Authorization:String,
        @Query("lastId") lastId: Int,
        @Query("limit") limit: Int
    ): Call<ResponseWrapper<Array<ResponseMainBody>>>

    @GET("/sns/user/me/like/posts")
    fun requestMyLikePosts(
        @Header("Authorization") Authorization:String,
        @Query("lastId") lastId: Int,
        @Query("limit") limit: Int
    ): Call<ResponseWrapper<Array<ResponseMainBody>>>

    /*Search*/
    //search
    @GET("/sns/search/{text}")
    fun requestSearch(
        @Header("Authorization") Authorization:String,
        @Path("text") text: String?,
        @Query("lastId") lastId: Int,
        @Query("limit") limit: Int
    ): Call<LookUpList>

    //hashtag
    @GET("/sns/hashtag/{text}")
    fun requestHashtag(
            @Header("Authorization") Authorization:String,
            @Path("text") text: String?,
            @Query("lastId") lastId: Int,
            @Query("limit") limit: Int
    ): Call<ResponseWrapper<Array<ResponseMainBody>>>

    /*upload Image*/
    @Multipart
    @POST("/sns/upload")
    fun requestUploadImage(
        @Part images: MultipartBody.Part
    ):Call<ResponseWrapper<Array<String>>>

    //upload multiple Image
    @Multipart
    @POST("/sns/upload")
    fun requestUploadMultipleImage(
        @Part images: ArrayList<MultipartBody.Part>
    ): Call<ResponseImage>

    /*category*/
    @GET("/sns/category/interests")
    fun getCategory(): Call<ResponseWrapper<List<String>>>

    @GET("/sns/category/majors")
    fun getMajors(): Call<ResponseWrapper<List<String>>>
}