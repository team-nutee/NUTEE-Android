package kr.nutee.nutee_android.data.main.home

data class Retweet(
    val Comments: List<Any>,
    val Images: List<ImageX>,
    val Likers: List<Any>,
    val RetweetId: Any,
    val User: User,
    val UserId: Int,
    val content: String,
    val createdAt: String,
    val id: Int,
    val isBlocked: Boolean,
    val isDeleted: Boolean,
    val updatedAt: String
)