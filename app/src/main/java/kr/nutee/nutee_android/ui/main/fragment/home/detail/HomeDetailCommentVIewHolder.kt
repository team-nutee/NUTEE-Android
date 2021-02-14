package kr.nutee.nutee_android.ui.main.fragment.home.detail

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.DateParser
import kr.nutee.nutee_android.data.TestToken
import kr.nutee.nutee_android.data.main.home.CommentBody
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.customEnqueue
import kr.nutee.nutee_android.ui.extend.dialog.customSelectDialog
import kr.nutee.nutee_android.ui.extend.imageSetting.setImageURLSetting

class HomeDetailCommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

	private val img_comment_profile: ImageView = itemView.findViewById<ImageView>(R.id.img_comment_profile)
	private val text_commnet_nick = itemView.findViewById<TextView>(R.id.text_commnet_nick)
	private val text_comment_content = itemView.findViewById<TextView>(R.id.text_comment_content)
	private val text_comment_updateAt = itemView.findViewById<TextView>(R.id.text_comment_updateAt)

	private val more_button = itemView.findViewById<ImageView>(R.id.img_comment_more)

    fun bind(customData: CommentBody, postId: Int?, context: Context) {
		Glide.with(itemView).load(
			setImageURLSetting(
				customData.user?.Image?.src
			)
		).into(img_comment_profile)
		text_commnet_nick.text = customData.user?.nickname
		text_comment_content.text = customData.content
		text_comment_updateAt.text = customData.updatedAt?.let { DateParser(it).calculateDiffDate() }
		more_button.setOnClickListener{
			moreEvent(it, customData,postId,context)
		}
    }

	private fun moreEvent(view: View, customData: CommentBody, postId: Int?,context: Context) {
		if (customData.user?.id.toString() == TestToken.testMemberId.toString()) {
			itemView.context.customSelectDialog(View.GONE, View.VISIBLE, View.VISIBLE, {},
				{ Log.d("댓글수정 버튼", "누름")
					//rewritePost(customData)
				},
				{ Log.d("글삭제 버튼", "누름")
					RequestToServer.backService.requestDelComment(
						"Bearer "+ TestToken.testToken,
						postId,
						customData.id
					).customEnqueue(
							onSuccess = {
								(context as HomeDetailActivity).finish()
//								val intent=Intent(context,HomeDetailActivity::class.java)
//								context.startActivity(intent)
							},
							onError = {
								Toast.makeText(itemView.context,"네트워크 오류", Toast.LENGTH_SHORT)
									.show()}
						)
				})
		}
	}
}