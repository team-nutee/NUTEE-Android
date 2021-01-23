package kr.nutee.nutee_android.data.main.add

data class RequestPost(
	val title:String,
	val content:String,
	val image: ArrayList<String>?,
	val category:String
)