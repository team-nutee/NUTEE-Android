package kr.nutee.nutee_android.data.main.profile

import kr.nutee.nutee_android.data.ProfileImage

data class ProfileBody(
	val id: Int,
	val nickname: String,
	val profileUrl: ProfileImage,
	val interests: ArrayList<String>,
	val majors: ArrayList<String>
)
