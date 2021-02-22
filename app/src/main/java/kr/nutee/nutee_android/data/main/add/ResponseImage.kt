package kr.nutee.nutee_android.data.main.add

import kr.nutee.nutee_android.data.main.home.Image

data class ResponseImage(
	val code:Number?,
	val message:String?,
	val body:Array<Image>
)
