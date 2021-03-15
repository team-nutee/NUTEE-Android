package kr.nutee.nutee_android.data.member.register

data class RequestRegister(
        val userId: String,
        val nickname: String,
        val schoolEmail: String,
        val password: String,
        val otp:String,
        val interests:List<String>,
        val majors:List<String>
)