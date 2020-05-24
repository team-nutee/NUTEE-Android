package kr.nutee.nutee_android.data.member.register

data class ResponseRegister(
    val createdAt: String,
    val id: Int,
    val nickname: String,
    val password: String,
    val schoolEmail: String,
    val updatedAt: String,
    val userId: String
)