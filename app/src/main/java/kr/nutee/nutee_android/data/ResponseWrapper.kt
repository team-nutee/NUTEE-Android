package kr.nutee.nutee_android.data

data class ResponseWrapper<T>(
    val code: Int,
    val message: String,
    val body: T,
)
