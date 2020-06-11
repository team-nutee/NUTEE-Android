package kr.nutee.nutee_android.data.member.login

data class ResponseLogin(
    val Followers: List<Any>,
    val Followings: List<Any>,
    val Image: String,
    val Posts: List<Any>,
    val id: Int,
    val nickname: String,
    val userId: String
)