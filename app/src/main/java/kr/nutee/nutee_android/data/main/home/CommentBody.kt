package kr.nutee.nutee_android.data.main.home

data class CommentBody (
	val id:Int?,
	val content:String?,
	val likers:Array<Liker>?,
	val createdAt:String?,
	val updatedAt:String?,
	val reComment:Array<ReComment>?,
	val user:User?
)