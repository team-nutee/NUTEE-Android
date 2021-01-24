package kr.nutee.nutee_android.ui.main.fragment.home

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.App
import kr.nutee.nutee_android.data.DateParser
import kr.nutee.nutee_android.data.main.RequestReport
import kr.nutee.nutee_android.data.main.home.Body
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.customEnqueue
import kr.nutee.nutee_android.ui.extend.dialog.cumstomReportDialog
import kr.nutee.nutee_android.ui.extend.dialog.customSelectDialog
import kr.nutee.nutee_android.ui.main.fragment.add.AddActivity
import kr.nutee.nutee_android.ui.main.fragment.home.detail.HomeDetailActivity


/*home fragment RecyclerView 내부 하나의 뷰의 정보를 지정하는 클래스 */
class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

	val requestToServer = RequestToServer

	val category = itemView.findViewById<TextView>(R.id.text_main_home_recyclerview_item_category)
	val title = itemView.findViewById<TextView>(R.id.text_main_home_recyclerview_item_title)
	val content = itemView.findViewById<TextView>(R.id.text_main_home_recyclerview_item_content)
	val text_main_home_count_image = itemView.findViewById<TextView>(R.id.text_main_home_recyclerview_item_img)
	val text_main_home_count_comment = itemView.findViewById<TextView>(R.id.text_main_home_recyclerview_item_comment)
	val text_main_home_count_like = itemView.findViewById<TextView>(R.id.text_main_home_recyclerview_item_likes)
	val text_main_home_updateat = itemView.findViewById<TextView>(R.id.text_main_home_recyclerview_item_date)
	val text_main_home_hits = itemView.findViewById<ImageView>(R.id.text_main_home_recyclerview_item_hits)
	val img_main_home_img = itemView.findViewById<ImageView>(R.id.img_main_home_recyclerview_item_img)
	val img_main_home_likes = itemView.findViewById<ImageView>(R.id.img_main_home_recyclerview_item_likes)
	val img_main_home_hits = itemView.findViewById<ImageView>(R.id.img_main_home_recyclerview_item_hits)
	val img_main_home_comment = itemView.findViewById<TextView>(R.id.img_main_home_recyclerview_item_comment)
	val img_main_home_more=itemView.findViewById<TextView>(R.id.img_main_home_recyclerview_item_more)

	fun bind(
		customData: Body
	) {
		category.text = customData.category
		text_main_home_updateat.text = customData.UpdatedAt
		title.text=customData.title
		content.text = customData.content
		setLikeEvent(img_main_home_likes,customData)
		text_main_home_count_image.text = customData.images?.size.toString()
		text_main_home_count_comment.text = customData.commentNum.toString()
		text_main_home_count_like.text = customData.likers?.size.toString()

		itemView.setOnClickListener{
			Log.d("DetailClick",customData.id.toString())
			//FIXME 아이디가 없는경우 존재하지 않는 글입니다 띄워주기! 그리고 자체적으로 리스트에서 삭제하고 적용하기.
			val gotoDetailPageIntent = Intent(itemView.context, HomeDetailActivity::class.java)
			gotoDetailPageIntent.putExtra("Detail_id", customData.id!!)
			itemView.context.startActivity(gotoDetailPageIntent)
		}
		img_main_home_more.setOnClickListener{
			moreEvent(it,customData)
		}
		img_main_home_likes.setOnClickListener {
			likeClickEvent(it, customData)
		}
	}

	private fun setLikeEvent(it: View, customData: Body) {
		val boolLike = customData.likers?.any{ liker ->
			liker.id == App.prefs.local_user_id.toInt()
		}
		if (boolLike != null) {
			it.isActivated = boolLike
		}
	}

	private fun likeClickEvent(
		view: View,
		customData: Body
	) {
		if (view.isActivated) {
			//좋아요 버튼 눌림
			requestToServer.backService.requestDelLike(App.prefs.local_login_token, customData.id)
				.customEnqueue(
					onSuccess = {
						view.isActivated = false
						text_main_home_count_like.text = (text_main_home_count_like.text.toString().toInt() - 1).toString()
					},
					onError = {}
				)
//				.customEnqueue { res->
//					if (res.isSuccessful) {
//						it.isActivated = false
//						text_main_home_count_like.text = (text_main_home_count_like.text.toString().toInt() - 1).toString()
//					}
//				}

		} else {
			//좋아요 안눌림
			requestToServer.backService.requestLike(App.prefs.local_login_token, customData.id)
				.customEnqueue(
					onSuccess = {
						view.isActivated = true
						text_main_home_count_like.text = (text_main_home_count_like.text.toString().toInt() + 1).toString()
					},
					onError = {}
				)
//				.customEnqueue { res->
//					if (res.isSuccessful) {
//						it.isActivated = true
//						text_main_home_count_like.text = (text_main_home_count_like.text.toString().toInt() + 1).toString()
//					}
//				}
		}
	}

	private fun moreEvent(it:View, customData: Body) {
		if (customData.User?.id.toString() == App.prefs.local_user_id) {
			itemView.context.customSelectDialog(View.GONE, View.VISIBLE, View.VISIBLE,
				{},
				{
					Log.d("글수정 버튼", "누름")
					fixPost(customData)
				},
				{
					Log.d("글삭제 버튼", "누름")
					requestToServer.backService.requestDelete(
						App.prefs.local_login_token,
						customData.id)
						.customEnqueue(
							onSuccess = {HomeFragement()},
							onError = {}
						)
				})
		} else {
			itemView.context.customSelectDialog(View.VISIBLE, View.GONE, View.GONE,
				{
					Log.d("글신고", "누름")
					it.context.cumstomReportDialog{
						requestToServer.backService.requestReport(
							RequestReport(it), customData.id)
							.customEnqueue(
								onSuccess = {
									Toast
										.makeText(itemView.context,"신고가 성공적으로 접수되었습니다.",Toast.LENGTH_SHORT)
										.show()
								},
								onError = {}
							)
//							.customEnqueue{ res->
//								if (res.isSuccessful) {
//									Toast
//										.makeText(itemView.context,"신고가 성공적으로 접수되었습니다.",Toast.LENGTH_SHORT)
//										.show()
//								}
//							}
					}
				})
		}
	}

	private fun fixPost(customData: Body) {
		val intent = Intent(itemView.context, AddActivity::class.java)
		intent.putExtra("title",customData.title)
		intent.putExtra("content",customData.content)
		intent.putExtra("category",customData.category)
		intent.putExtra("postId",customData.id)
		val imageArrayList = arrayListOf<String>()
		customData.images?.forEach{
			imageArrayList.add(it.src)
		}
		intent.putStringArrayListExtra("image", imageArrayList)
		itemView.context.startActivity(intent)
	}

}

