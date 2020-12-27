package kr.nutee.nutee_android.data.main.home

import java.util.*

data class ResponseMainItem(
    val Comments: List<Comment>,
    val Images: List<Image>,
    val Likers: List<Liker>,
    /*val Retweet: Any,
    val RetweetId: Any,*/
    val User: User?,
    val UserId: Int?,
    val hits:Int?,
    val category: String?,
    val title:String?,
    val content: String?,
    val createdAt: String?,
    val id: Int?,
    val isBlocked: Boolean,
    val isDeleted: Boolean,
    val updatedAt: String?
)