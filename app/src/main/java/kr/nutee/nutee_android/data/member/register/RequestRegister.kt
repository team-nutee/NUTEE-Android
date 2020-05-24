package kr.nutee.nutee_android.data.member.register

data class RequestRegister(
    val nickname: String,
    val password: String,
    val schoolEmail: String,
    val userId: String
)