package kr.nutee.nutee_android.data.member.register

data class ResponseRegister(
        val id: Int,
        val nickname: String,
        val profileUrl:String?,
        val interests:List<String>,
        val majors:List<String>
)