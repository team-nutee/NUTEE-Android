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
import kr.nutee.nutee_android.data.App
import kr.nutee.nutee_android.data.DateParser
import kr.nutee.nutee_android.data.main.RequestReport
import kr.nutee.nutee_android.data.main.home.CommentBody
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.*
import kr.nutee.nutee_android.ui.extend.dialog.cumstomReportDialog

class HomeDetailCommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

	private val img_comment_profile: ImageView = itemView.findViewById(R.id.img_comment_profile)
	private val text_commnet_nick = itemView.findViewById<TextView>(R.id.text_commnet_nick)
	private val text_comment_content = itemView.findViewById<TextView>(R.id.text_comment_content)
	private val text_comment_updateAt = itemView.findViewById<TextView>(R.id.text_comment_updateAt)
	private val recyclerView_Reply = itemView.findViewById<RecyclerView>(R.id.rv_comment_reply)
	private val more_button = itemView.findViewById<ImageView>(R.id.img_comment_more)
	private val img_detail_favorit_btn=itemView.findViewById<ImageView>(R.id.img_detail_comment_favorit_btn)
	private val text_detail_favorit_count=itemView.findViewById<TextView>(R.id.text_detail_comment_favorit_count)
	private val textRewrite = itemView.findViewById<TextView>(R.id.text_comment_rewrite)

	lateinit var homeDetailReplyAdapter:HomeDetailReplyAdapter
	private var commentId:Int?=0

    fun bind(
			customData: CommentBody,
			postId: Int?,
			context: Context
	) {
		commentId=customData.id

		//답글 목록 생성
		if(!customData.reComment.isNullOrEmpty()){
			homeDetailReplyAdapter= HomeDetailReplyAdapter(context, customData.reComment,postId, commentId)
			recyclerView_Reply.adapter=homeDetailReplyAdapter
		}


		setLikeEvent(img_detail_favorit_btn,text_detail_favorit_count,customData.likers)
		//댓글 프로필 이미지 설정
		Glide.with(itemView)
			.load(customData.user?.image?.src)
			.fallback(R.mipmap.nutee_character_background_white_round)
			.into(img_comment_profile)
		text_commnet_nick.text = customData.user?.nickname
		text_comment_content.text = customData.content
		text_comment_updateAt.text = customData.updatedAt?.let { DateParser(it).calculateDiffDate()}
		//수정 여부 표시
		if(customData.updatedAt !=customData.createdAt)
			textRewrite.visibility=View.VISIBLE

		more_button.setOnClickListener{
			moreEvent(customData,postId,context)
		}
		img_detail_favorit_btn.setOnClickListener {
			likeClickEvent(it,postId,commentId,text_detail_favorit_count)
		}
    }

	private fun moreEvent(customData: CommentBody, postId: Int?,context: Context) {
		itemView.context.contentMoreEvent(
			customData.user,
			View.VISIBLE,
			{//답글 기능
				val intent=Intent(context,HomeDetailActivity::class.java)
				intent.putExtra("reply",true)
				intent.putExtra("comment_id",customData.id)
				intent.putExtra("Detail_id",postId)
				(context as HomeDetailActivity).finish()
				context.startActivityForResult(intent,0)
			},
			{//댓글 수정
				val intent=Intent(context,HomeDetailActivity::class.java)
				intent.putExtra("rewriteComment",customData.content)
				intent.putExtra("comment_id",customData.id)
				intent.putExtra("Detail_id",postId)
				(context as HomeDetailActivity).finish()
				context.startActivityForResult(intent,0)
			},
			{//댓글 삭제
				RequestToServer.backService.requestDelComment(
						"Bearer "+ App.prefs.local_login_token,
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
			},
			{//댓글 신고
				itemView.context.cumstomReportDialog("이 댓글을 신고하시겠습니까?"){
					RequestToServer.backService.requestReportComment(
							"Bearer "+ App.prefs.local_login_token,
							postId,
							customData.id,
							RequestReport(it)
					).customEnqueue(
							onSuccess = {
								Toast.makeText(itemView.context,
										"신고가 성공적으로 접수되었습니다.",
										Toast.LENGTH_SHORT).show()
							}
					)}
			}
		)
	}

	private fun likeClickEvent(
		view: View,
		postId: Int?,
		commentId: Int?,
		countView: TextView
	) {
		if (view.isActivated) {//이미 좋아요한 경우
			RequestToServer.backService.requestDelLikecomment(
					"Bearer "+ App.prefs.local_login_token,
				postId,
				commentId
			).customEnqueue(
					onSuccess = {
						loadUnLike(view,countView)
					},
					onError = {
						Log.d("Network", "댓글 좋아요 취소 에러")
					})
		} else {
			RequestToServer.backService.requestLikecomment(
					"Bearer "+ App.prefs.local_login_token,
				postId,
				commentId)
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