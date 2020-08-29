package kr.nutee.nutee_android.data.main.search

data class RequestSearch(
	val text:String,
	val lastId:Int,
	val limit: Int
)