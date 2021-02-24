package kr.nutee.nutee_android.data.main.add

import kr.nutee.nutee_android.data.main.home.Image

data class RequestPost(
	val title:String,
	val content:String,
	val image: Array<Image>?,
	val category:String
)