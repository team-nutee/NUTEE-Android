package kr.nutee.nutee_android.data.main.home

data class Body(
	val id:Number?,
	val title:String?,
	val content:String?,
	val CreatedAt:String?,
	val UpdatedAt:String?,
	val User:User?,
	val images:Array<Image>?,
	val likers:Array<Liker>?,
	val commentNum: Number?,
	val retweet:Retweet?,
	val category: String?,
	val hits:Number?,
	val blocked:Boolean?
)