package kr.nutee.nutee_android.data.main.add

import kr.nutee.nutee_android.data.main.home.Image

data class RequestRewritePost(
	val title:String,
	val content:String,
	val image: Array<Image?>?
)