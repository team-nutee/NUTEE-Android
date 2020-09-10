package kr.nutee.nutee_android.data.main.profile

import kr.nutee.nutee_android.data.ProfileImage

data class ResponseProfile(
	val id:Int,
	val nickname:String,
	val Posts:List<PostIds>?,
	val Image: ProfileImage?
)

data class PostIds(
	val id:Int?
)
