package kr.nutee.nutee_android.ui.main.fragment.home.detail

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
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
import kr.nutee.nutee_android.data.main.RequestReport
import kr.nutee.nutee_android.data.main.home.CommentBody
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.customEnqueue
import kr.nutee.nutee_android.ui.extend.dialog.cumstomReportDialog
import kr.nutee.nutee_android.ui.extend.dialog.customSelectDialog
import kr.nutee.nutee_android.ui.extend.imageSetting.setImageURLSetting

class HomeDetailCommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

	private val img_comment_profile: ImageView = itemView.findViewById<ImageView>(R.id.img_comment_profile)
	private val text_commnet_nick = itemView.findViewById<TextView>(R.id.text_commnet_nick)
	private val text_comment_content = itemView.findViewById<TextView>(R.id.text_comment_content)
	private val text_comment_updateAt = itemView.findViewById<TextView>(R.id.text_comment_updateAt)
	private val recyclerView_Reply = itemView.findViewById<RecyclerView>(R.id.rv_comment_reply)
	private val more_button = itemView.findViewById<ImageView>(R.id.img_comment_more)

	lateinit var homeDetailReplyAdapter:HomeDetailReplyAdapter

    fun bind(customData: CommentBody, postId: Int?, context: Context) {
		if(!customData.reComment.isNullOrEmpty()){
			Log.d("reComment", "답글 not null")
			homeDetailReplyAdapter= HomeDetailReplyAdapter(context, customData.reComment,postId, customData.id)
			recyclerView_Reply.adapter=homeDetailReplyAdapter
		}

		Glide.with(itemView).load(
			setImageURLSetting(
				customData.user?.Image?.src
			)
		).into(img_comment_profile)
		text_commnet_nick.text = customData.user?.nickname
		text_comment_content.text = customData.content
		text_comment_updateAt.text = customData.updatedAt?.let { DateParser(it).calculateDiffDate() }
		more_button.setOnClickListener{
			moreEvent(customData,postId,context)
		}
    }

	private fun moreEvent(customData: CommentBody, postId: Int?,context: Context) {
		if (customData.user?.id.toString() == TestToken.testMemberId.toString()) {
			itemView.context.customSelectDialog(View.GONE,View.VISIBLE, View.VISIBLE, View.VISIBLE, {},
				{Log.d("select button", "댓글 답글")
					val intent=Intent(context,HomeDetailActivity::class.java)
					intent.putExtra("reply",true)
					intent.putExtra("comment_id",customData.id)
					intent.putExtra("Detail_id",postId)
					(context as HomeDetailActivity).finish()
					context.startActivityForResult(intent,0)
				},
				{ Log.d("select button", "댓글 수정")
					val intent=Intent(context,HomeDetailActivity::class.java)
					intent.putExtra("rewriteComment",customData.content)
					intent.putExtra("comment_id",customData.id)
					intent.putExtra("Detail_id",postId)
					(context as HomeDetailActivity).finish()
					context.startActivityForResult(intent,0)
				},
				{ Log.d("select button", "댓글 삭제")
					RequestToServer.backService.requestDelComment(
						"Bearer "+ TestToken.testToken,
						postId,
						customData.id
					).customEnqueue(
							onSuccess = {},
							onError = {
								Toast.makeText(itemView.context,"네트워크 오류", Toast.LENGTH_SHORT)
									.show()}
						)
					(context as HomeDetailActivity).finish()
					val intent=Intent(context,HomeDetailActivity::class.java)
					intent.putExtra("Detail_id",postId)
					context.startActivity(intent)
				})
		}else{
			itemView.context.customSelectDialog(View.VISIBLE, View.GONE, View.GONE, View.GONE,
				{ Log.d("select button", "댓글 신고")
					itemView.context.cumstomReportDialog("이 댓글을 신고하시겠습니까?"){
						RequestToServer.backService.requestReportComment(
							"Bearer " + TestToken.testToken,
							postId,
							customData.id,
							RequestReport(it)
						).customEnqueue(
							onSuccess = {
								Toast.makeText(
									itemView.context,
									"신고가 성공적으로 접수되었습니다.",
									Toast.LENGTH_SHORT
								).show()
							}
						)}
				})
		}
	}
}