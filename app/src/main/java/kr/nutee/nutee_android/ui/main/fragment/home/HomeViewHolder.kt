package kr.nutee.nutee_android.ui.main.fragment.home

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.main.home.ResponseMainItem
import kr.nutee.nutee_android.network.RequestToServer


/*home fragment RecyclerView 내부 하나의 뷰의 정보를 지정하는 클래스 */
class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
	val baseUrl = RequestToServer.retrofit.baseUrl().toString()
	val profileImg = itemView.findViewById<ImageView>(R.id.img_list_profile)
	val profileName = itemView.findViewById<TextView>(R.id.text_main_username)
	val content = itemView.findViewById<TextView>(R.id.text_main_content)
	val btn_favorite = itemView.findViewById<ImageView>(R.id.img_main_count_like)

	fun bind(customData: ResponseMainItem) {
		val userImageLoad = imageSetting(customData)
		Log.d("ImageLoading", userImageLoad)
		Glide.with(itemView).load(userImageLoad).into(profileImg)
		profileName.text = customData.User.nickname
		content.text = customData.content
	}

	private fun imageSetting(customData: ResponseMainItem):String {
		return "${baseUrl}${customData.User.Image?.src?:"settings/nutee_profile.png"}"
	}
}

