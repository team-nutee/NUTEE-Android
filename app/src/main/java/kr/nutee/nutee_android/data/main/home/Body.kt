package kr.nutee.nutee_android.data.main.home

data class Body(
	val id:Number?,
	val title:String?,
	val content:String?,
	val CreatedAt:String?,
	val UpdatedAt:String?,
	val user:User?,
	val images:ArrayList<String>?,
	val likers:ArrayList<Liker>?,
	val commentNum: Number?,
	val retweet:Retweet?,
	val category: String?,
	val hits:Number?,
	val blocked:Boolean?
)