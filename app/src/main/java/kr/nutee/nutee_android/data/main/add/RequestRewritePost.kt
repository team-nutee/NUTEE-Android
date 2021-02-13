package kr.nutee.nutee_android.data.main.add

import android.net.Uri
import okhttp3.MultipartBody

data class RequestRewritePost(
	val title:String,
	val content:String,
	val image: ArrayList<String>?
)