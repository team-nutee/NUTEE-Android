package kr.nutee.nutee_android.ui.main.fragment.home.detail

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.RecyclerView
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.App
import kr.nutee.nutee_android.data.DateParser
import kr.nutee.nutee_android.data.main.RequestReport
import kr.nutee.nutee_android.data.main.home.ReComment
import kr.nutee.nutee_android.databinding.MainHomeDetailItemBinding
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.*
import kr.nutee.nutee_android.ui.extend.dialog.cumstomReportDialog
import kr.nutee.nutee_android.ui.extend.dialog.customSelectDialog

class HomeDetailReplyViewHolder(private val binding: MainHomeDetailItemBinding) : RecyclerView.ViewHolder(binding.root) {

	/*private val img_comment_profile: ImageView = itemView.findViewById<ImageView>(R.id.img_comment_profile)
	private val text_commnet_nick = itemView.findViewById<TextView>(R.id.text_commnet_nick)
	private val text_comment_content = itemView.findViewById<TextView>(R.id.text_comment_content)
	private val text_comment_updateAt = itemView.findViewById<TextView>(R.id.text_comment_updateAt)
	private val img_detail_comment_favorit_btn=itemView.findViewById<ImageView>(R.id.img_detail_comment_favorit_btn)
	private val text_detail_comment_favorit_count=itemView.findViewById<TextView>(R.id.text_detail_comment_favorit_count)
	private val more_button=itemView.findViewById<ImageView>(R.id.img_comment_more)
	private val textRewrite = itemView.findViewById<TextView>(R.id.text_comment_rewrite)*/

	fun bindWithView(reComment: ReComment, context: Context, postId: Int?, commentId: Int?) {
		//답글 프로필 이미지 설정
		GlideApp.with(itemView)
			.load(reComment.user?.image?.src)
			.placeholder(R.drawable.ic_baseline_rotate_left_24)
			.error(R.mipmap.nutee_character_background_white_round)
			.fallback(R.mipmap.nutee_character_background_white_round)
			.into(binding.imgCommentProfile)
		binding.textCommnetNick.text = reComment.user?.nickname
		binding.textCommentContent.text = reComment.content
		binding.textCommentUpdateAt.text = reComment.updatedAt?.let { DateParser(it).calculateDiffDate() }
		setLikeEvent(binding.imgDetailCommentFavoritBtn,binding.textDetailCommentFavoritCount,reComment.likers)
		//수정 여부 표시
		if(reComment.updatedAt !=reComment.createdAt)
			binding.textCommentRewrite.visibility=View.VISIBLE

		binding.imgDetailCommentFavoritBtn.setOnClickListener {
			likeClickEvent(it, postId, reComment.id, binding.textDetailCommentFavoritCount)
		}
		binding.imgCommentMore.setOnClickListener{
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
				//context.startActivityForResult(intent,0)
				context.getContent.launch(intent)
			},
			{//답글 삭제
				RequestToServer.snsService.requestDelComment(
						"Bearer "+ App.prefs.local_login_token,
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
							RequestToServer.snsService.requestReportComment(
									"Bearer "+ App.prefs.local_login_token,
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
			RequestToServer.snsService.requestDelLikecomment(
					"Bearer "+ App.prefs.local_login_token,
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
			RequestToServer.snsService.requestLikecomment(
					"Bearer "+ App.prefs.local_login_token,
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