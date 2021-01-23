package kr.nutee.nutee_android.data.main.home

data class Retweet (
	val id:Number?,
	val title:String?,
	val content:String?,
	val CreatedAt:String?,
	val UpdatedAt:String?,
	val blocked:Boolean?,
	val user:User?,
	val images:ArrayList<Image>?,
	val likers:ArrayList<Liker>?,
	val commentNum: Number?,
	val category: String?,
	val hits:Number?,
	val deleted:Boolean?
)