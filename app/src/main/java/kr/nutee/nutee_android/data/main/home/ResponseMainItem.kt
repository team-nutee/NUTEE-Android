package kr.nutee.nutee_android.data.main.home

data class ResponseMainItem(
    val Comments: List<Any>,
    val Images: List<Image>,
    val Likers: List<Liker>,
    val Retweet: Retweet,
    val RetweetId: Int,
    val User: UserX,
    val UserId: Int,
    val content: String,
    val createdAt: String,
    val id: Int,
    val isBlocked: Boolean,
    val isDeleted: Boolean,
    val updatedAt: String
)