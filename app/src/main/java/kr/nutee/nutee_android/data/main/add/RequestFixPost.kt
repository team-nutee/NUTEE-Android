package kr.nutee.nutee_android.data.main.add

import android.net.Uri
import okhttp3.MultipartBody

data class RequestFixPost(
	val postId: Int,
	val content:String,
	val image: ArrayList<String>?
)