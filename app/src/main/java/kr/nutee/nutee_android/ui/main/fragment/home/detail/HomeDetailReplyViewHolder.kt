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
import kotlinx.android.synthetic.main.main_home_detail_activtiy.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.DateParser
import kr.nutee.nutee_android.data.TestToken
import kr.nutee.nutee_android.data.main.RequestReport
import kr.nutee.nutee_android.data.main.home.ReComment
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.*
import kr.nutee.nutee_android.ui.extend.dialog.cumstomReportDialog
import kr.nutee.nutee_android.ui.extend.dialog.customSelectDialog
import kr.nutee.nutee_android.ui.extend.imageSetting.setImageURLSetting

class HomeDetailReplyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

	private val img_comment_profile: ImageView = itemView.findViewById<ImageView>(R.id.img_comment_profile)
	private val text_commnet_nick = itemView.findViewById<TextView>(R.id.text_commnet_nick)
	private val text_comment_content = itemView.findViewById<TextView>(R.id.text_comment_content)
	private val text_comment_updateAt = itemView.findViewById<TextView>(R.id.text_comment_updateAt)
	private val img_detail_comment_favorit_btn=itemView.findViewById<ImageView>(R.id.img_detail_comment_favorit_btn)
	private val text_detail_comment_favorit_count=itemView.findViewById<TextView>(R.id.text_detail_comment_favorit_count)
	private val more_button=itemView.findViewById<ImageView>(R.id.img_comment_more)
	private val textRewrite = itemView.findViewById<TextView>(R.id.text_comment_rewrite)

	fun bindWithView(reComment: ReComment, context: Context, postId: Int?, commentId: Int?) {
		//답글 프로필 이미지 설정
		Glide.with(itemView)
			.load(reComment.user?.image?.src)
			.fallback(R.mipmap.nutee_character_background_white_round)
			.into(img_comment_profile)
		text_commnet_nick.text = reComment.user?.nickname
		text_comment_content.text = reComment.content
		text_comment_updateAt.text = reComment.updatedAt?.let { DateParser(it).calculateDiffDate() }
		setLikeEvent(img_detail_comment_favorit_btn,text_detail_comment_favorit_count,reComment.likers)
		//수정 여부 표시
		if(reComment.updatedAt !=reComment.createdAt)
			textRewrite.visibility=View.VISIBLE

		img_detail_comment_favorit_btn.setOnClickListener {
			likeClickEvent(it, postId, reComment.id, text_detail_comment_favorit_count)
		}
		more_button.setOnClickListener{
			moreEvent(reComment,postId,context)
		}
	}

	private fun moreEvent(reComment: ReComment, postId: Int?, context: Context) {
		itemView.context.contentMoreEvent(
			reComment.user,
			View.GONE,{},
			{//답글 수정
				val intent= Intent(context,HomeDetailActivity::class.java)
				intent.putExtra("rewriteComment",reComment.content)
				intent.putExtra("comment_id",reComment.id)
				intent.putExtra("Detail_id",postId)
				(context as HomeDetailActivity).finish()
				context.startActivityForResult(intent,0)
			},
			{//답글 삭제
				RequestToServer.backService.requestDelComment(
					"Bearer "+ TestToken.testToken,
					postId,
					reComment.id
				).customEnqueue(
					onSuccess = {},
					onError = {
						Toast.makeText(itemView.context,"네트워크 오류", Toast.LENGTH_SHORT)
							.show()}
				)
				(context as HomeDetailActivity).finish()
				val intent= Intent(context,HomeDetailActivity::class.java)
				intent.putExtra("Detail_id",postId)
				context.startActivity(intent)
			},
			{//답글 신고
				itemView.context.customSelectDialog(View.VISIBLE, View.GONE, View.GONE, View.GONE,
					{ Log.d("select button", "댓글 신고")
						itemView.context.cumstomReportDialog("이 댓글을 신고하시겠습니까?"){
							RequestToServer.backService.requestReportComment(
								"Bearer " + TestToken.testToken,
								postId,
								reComment.id,
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
		)
	}

	private fun likeClickEvent(
			view: View,
			postId: Int?,
			reCommentId: Int?,
			countView: TextView
	) {
		if (view.isActivated) {//이미 좋아요한 경우
			RequestToServer.backService.requestDelLikecomment(
					"Bearer "+ TestToken.testToken,
					postId,
				reCommentId
			).customEnqueue(
					onSuccess = {
						loadUnLike(view,countView)
					},
					onError = {
						Log.d("Network", "댓글 좋아요 취소 에러")
					})
		} else {
			RequestToServer.backService.requestLikecomment(
					"Bearer "+ TestToken.testToken,
					postId,
				reCommentId)
					.customEnqueue(
							onSuccess = {
								loadLike(view,countView,it.body()?.body?.likers)
							},
							onError = {
								Log.d("Network", "댓글 좋아요 설정 에러")
							})
		}
	}
}