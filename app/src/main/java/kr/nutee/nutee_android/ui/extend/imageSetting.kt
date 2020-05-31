package kr.nutee.nutee_android.ui.extend

import kr.nutee.nutee_android.network.RequestToServer

fun imageSetting(url: String?) : String {
	val baseUrl = RequestToServer.retrofit.baseUrl().toString()

	return "${baseUrl}${url?:"settings/nutee_profile.png"}"
}
