package kr.nutee.nutee_android.data.main.home

data class Comment(
    val code:Number?,
    val message:String?,
    val body:ArrayList<CommentBody>?,
    val links:Links?
)