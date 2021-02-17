package kr.nutee.nutee_android.ui.main.fragment.home.detail

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.DateParser
import kr.nutee.nutee_android.data.main.home.ReComment
import kr.nutee.nutee_android.ui.extend.imageSetting.setImageURLSetting

class HomeDetailReplyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

	private val img_comment_profile: ImageView = itemView.findViewById<ImageView>(R.id.img_comment_profile)
	private val text_commnet_nick = itemView.findViewById<TextView>(R.id.text_commnet_nick)
	private val text_comment_content = itemView.findViewById<TextView>(R.id.text_comment_content)
	private val text_comment_updateAt = itemView.findViewById<TextView>(R.id.text_comment_updateAt)

	fun bindWithView(reComment: ReComment, context: Context, postId: Int?, commentId: Int?) {
		Glide.with(itemView).load(
			setImageURLSetting(
				reComment.user?.Image?.src
			)
		).into(img_comment_profile)
		text_commnet_nick.text = reComment.user?.nickname
		text_comment_content.text = reComment.content
		text_comment_updateAt.text = reComment.updatedAt?.let { DateParser(it).calculateDiffDate() }
		//more_button.setOnClickListener{
		//	moreEvent(reComment,postId,context)
		//}
	}
}