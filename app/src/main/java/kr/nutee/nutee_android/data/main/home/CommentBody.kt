package kr.nutee.nutee_android.data.main.home

data class CommentBody (
	val id:Number?,
	val content:String?,
	val CreatedAt:String?,
	val UpdatedAt:String?,
	val reComment:ArrayList<ReComment>?,
	val user:User?
)