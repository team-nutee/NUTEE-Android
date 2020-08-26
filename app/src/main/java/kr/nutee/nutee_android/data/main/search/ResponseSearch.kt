package kr.nutee.nutee_android.data.main.search

import kr.nutee.nutee_android.data.main.home.Comment
import kr.nutee.nutee_android.data.main.home.Image
import kr.nutee.nutee_android.data.main.home.Liker
import kr.nutee.nutee_android.data.main.home.User

data class ResponseSearch(
	val id:Int,
	val content:String,
	val isDeleted:Boolean,
	val isBlocked:Boolean,
	val createdAt:String,
	val updatedAt:String,
	val UserId:Int,
	val RetweetId:Any?,
	val User:User,
	val Comments: List<Comment>,
	val Images: List<Image>,
	val Likers: List<Liker>,
	val Retweet:Any?
)