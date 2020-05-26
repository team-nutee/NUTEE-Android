package kr.nutee.nutee_android.ui.main.fragment.home

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.main.home.ResponseMainItem


/*home fragment RecyclerView 내부 하나의 뷰의 정보를 지정하는 클래스 */
class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
	val profileImg = itemView.findViewById<ImageView>(R.id.img_list_profile)
	val profileName = itemView.findViewById<TextView>(R.id.text_main_username)
	val content = itemView.findViewById<TextView>(R.id.text_main_content)
	val btn_favorite = itemView.findViewById<ImageView>(R.id.img_main_count_like)

	fun bind(customData: ResponseMainItem) {
		Glide.with(itemView).load(customData.User.Image.src).into(profileImg)
		profileName.text = customData.User.nickname
		content.text = customData.content
	}
}