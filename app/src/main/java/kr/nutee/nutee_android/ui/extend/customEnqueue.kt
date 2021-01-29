package kr.nutee.nutee_android.ui.extend

import android.util.Log
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


fun <ResponseType> Call<ResponseType>.customEnqueue(
	onFail:()->Unit = {},
	onSuccess: (Response<ResponseType>) -> Unit,
	onError:(Response<ResponseType>) -> Unit ={}
)
{
	this.enqueue(object : Callback<ResponseType> {
		override fun onFailure(call: Call<ResponseType>, t: Throwable) {
			Log.d("Network", "통신에 실패했습니다.")
			Log.d("Network", "${t.message}\n ${t.localizedMessage}\n")
			onFail()
		}

		override fun onResponse(call: Call<ResponseType>, response: Response<ResponseType>) {
			response.body()?.let {
				onSuccess(response)
			}?:onError(response)
		}
	})
}