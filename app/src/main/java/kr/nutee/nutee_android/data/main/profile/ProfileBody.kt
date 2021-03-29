package kr.nutee.nutee_android.data.main.profile

import kr.nutee.nutee_android.data.ProfileImage

data class ProfileBody(
	val id: Int,
	val nickname: String,
	val image: ProfileImage?,
	val interests: List<String>,
	val majors: List<String>,
	val postNum:Int,
	val commentNum:Int,
	val likeNum:Int
)
