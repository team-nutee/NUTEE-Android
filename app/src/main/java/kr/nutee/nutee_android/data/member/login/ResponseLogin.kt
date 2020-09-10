package kr.nutee.nutee_android.data.member.login

import kr.nutee.nutee_android.data.ProfileImage

data class ResponseLogin(
    val Followers: List<Any>,
    val Followings: List<Any>,
    val Image: ProfileImage?,
    val Posts: List<Any>,
    val id: Int,
    val nickname: String,
    val userId: String
)