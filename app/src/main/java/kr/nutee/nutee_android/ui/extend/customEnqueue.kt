package kr.nutee.nutee_android.ui.extend

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


fun <ResponseType> Call<ResponseType>.customEnqueue(
	onError:(Response<ResponseType>) -> Unit = {},
	onFail: () -> Unit = { Log.d("Network", "통신에 실패했습니다.") },
	onSuccess: (Response<ResponseType>) -> Unit
)
{
	val networkLog = "Network"
	this.enqueue(object : Callback<ResponseType> {
		override fun onFailure(call: Call<ResponseType>, t: Throwable) {
			Log.d("Network", "통신에 실패했습니다.")
			onFail()
		}

		override fun onResponse(call: Call<ResponseType>, response: Response<ResponseType>) {
			if (response.isSuccessful) {
				onSuccess(response)
				return
			}
			onError(response)
		}
	})
}