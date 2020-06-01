package kr.nutee.nutee_android.ui.main.fragment.home.detail

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.DateParser
import kr.nutee.nutee_android.data.main.home.Comment
import kr.nutee.nutee_android.ui.extend.imageSetting

class HomeDetailCommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

	val img_comment_profile = itemView.findViewById<ImageView>(R.id.img_comment_profile)
	val text_commnet_nick = itemView.findViewById<TextView>(R.id.text_commnet_nick)
	val text_comment_content = itemView.findViewById<TextView>(R.id.text_comment_content)
	val text_comment_updateAt = itemView.findViewById<TextView>(R.id.text_comment_updateAt)

    fun bind(customData: Comment) {
		Glide.with(itemView).load(imageSetting(customData.User?.Image?.src)).into(img_comment_profile)
		text_commnet_nick.text = customData.User?.nickname
		text_comment_content.text = customData.content
		text_comment_updateAt.text = DateParser(customData.updatedAt).calculateDiffDate()
    }
}