package kr.nutee.nutee_android.ui.main.fragment.home.detail

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.RecyclerView
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.App
import kr.nutee.nutee_android.data.DateParser
import kr.nutee.nutee_android.data.main.RequestReport
import kr.nutee.nutee_android.data.main.home.CommentBody
import kr.nutee.nutee_android.databinding.MainHomeDetailItemBinding
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.*
import kr.nutee.nutee_android.ui.extend.dialog.cumstomReportDialog

class HomeDetailCommentViewHolder(private val binding: MainHomeDetailItemBinding) : RecyclerView.ViewHolder(binding.root) {

	private val img_comment_profile: ImageView = binding.imgCommentProfile
	private val text_commnet_nick = binding.textCommnetNick
	private val text_comment_content = binding.textCommentContent
	private val text_comment_updateAt = binding.textCommentUpdateAt
	private val recyclerView_Reply = binding.rvCommentReply
	private val more_button = binding.imgCommentMore
	private val img_detail_favorit_btn=binding.imgDetailCommentFavoritBtn
	private val text_detail_favorit_count=binding.textDetailCommentFavoritCount
	private val textRewrite = binding.textCommentRewrite

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
		GlideApp.with(itemView)
			.load(customData.user?.image?.src)
			.placeholder(R.drawable.ic_baseline_rotate_left_24)
			.error(R.mipmap.nutee_character_background_white_round)
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
				//context.startActivityForResult(intent,0)
				context.getContent.launch(intent)
			},
			{//댓글 수정
				val intent=Intent(context,HomeDetailActivity::class.java)
				intent.putExtra("rewriteComment",customData.content)
				intent.putExtra("comment_id",customData.id)
				intent.putExtra("Detail_id",postId)
				(context as HomeDetailActivity).finish()
				//context.startActivityForResult(intent,0)
				context.getContent.launch(intent)
			},
			{//댓글 삭제
				RequestToServer.snsService.requestDelComment(
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
					RequestToServer.snsService.requestReportComment(
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
			RequestToServer.snsService.requestDelLikecomment(
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
			RequestToServer.snsService.requestLikecomment(
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