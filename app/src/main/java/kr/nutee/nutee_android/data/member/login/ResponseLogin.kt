package kr.nutee.nutee_android.data.member.login

data class ResponseLogin(
        val memberId:Int,
        val accessToken:String,
        val refreshToken:String,
)