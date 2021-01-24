package kr.nutee.nutee_android.ui.extend.imageSetting

import android.os.Environment
import android.util.Log
import kr.nutee.nutee_android.network.RequestToServer
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class createRequestToServerImage(){

	fun setImageTurePath(url: String?) : String {
		val baseUrl = RequestToServer.authRetrofit.baseUrl().toString()

		return "${baseUrl}${url?:"settings/nutee_profile.png"}"
	}



}