package kr.nutee.nutee_android.data.main.home

data class ReComment (
	val id:Number?,
	val likers:Array<Liker>?,
	val content:String?,
	val createdAt:String?,
	val updatedAt:String?,
	val user:User?
)