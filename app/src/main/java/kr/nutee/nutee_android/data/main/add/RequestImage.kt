package kr.nutee.nutee_android.data.main.add

import okhttp3.MultipartBody
import retrofit2.http.Part

data class RequestImage(
	@Part val image:MultipartBody.Part
)