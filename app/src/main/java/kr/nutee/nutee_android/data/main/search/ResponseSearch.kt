package kr.nutee.nutee_android.data.main.search

import kr.nutee.nutee_android.data.main.home.Comment
import kr.nutee.nutee_android.data.main.home.Image
import kr.nutee.nutee_android.data.main.home.Liker
import kr.nutee.nutee_android.data.main.home.User

data class ResponseSearch(
	val Comments: List<Comment>,
	val Images: List<Image>,
	val Likers: List<Liker>,
	val Retweet: Any,
	val RetweetId: Any,
	val User: User?,
	val UserId: Int?,
	val content: String?,
	val createdAt: String?,
	val id: Int?,
	val isBlocked: Boolean,
	val isDeleted: Boolean,
	val updatedAt: String?
)