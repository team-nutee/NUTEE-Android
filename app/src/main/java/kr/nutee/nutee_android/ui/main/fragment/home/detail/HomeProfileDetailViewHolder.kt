//package kr.nutee.nutee_android.ui.main.fragment.home.detail
//
//import android.content.Intent
//import android.util.Log
//import android.view.View
//import android.widget.ImageView
//import android.widget.TextView
//import android.widget.Toast
//import androidx.recyclerview.widget.RecyclerView
//import com.bumptech.glide.Glide
//import kr.nutee.nutee_android.R
//import kr.nutee.nutee_android.data.App
//import kr.nutee.nutee_android.data.DateParser
//import kr.nutee.nutee_android.data.main.RequestReport
//import kr.nutee.nutee_android.data.main.home.Body
//import kr.nutee.nutee_android.data.main.home.ResponseMainItem
//import kr.nutee.nutee_android.network.RequestToServer
//import kr.nutee.nutee_android.ui.extend.customEnqueue
//import kr.nutee.nutee_android.ui.extend.dialog.cumstomReportDialog
//import kr.nutee.nutee_android.ui.extend.dialog.customSelectDialog
//import kr.nutee.nutee_android.ui.extend.imageSetting.setImageURLSetting
//import kr.nutee.nutee_android.ui.main.fragment.add.AddActivity
//import kr.nutee.nutee_android.ui.main.fragment.home.HomeFragement
//
//class HomeProfileDetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//
//	val requestToServer = RequestToServer
//
//	val profileImg = itemView.findViewById<ImageView>(R.id.img_list_profile)
//	val profileName = itemView.findViewById<TextView>(R.id.text_main_username)
//	val content = itemView.findViewById<TextView>(R.id.text_main_content)
//	val btn_favorite = itemView.findViewById<ImageView>(R.id.img_main_count_like)
//	val text_main_count_image = itemView.findViewById<TextView>(R.id.ext_main_count_imaget)
//	val text_main_count_comment = itemView.findViewById<TextView>(R.id.text_main_count_comment)
//	val text_main_count_like = itemView.findViewById<TextView>(R.id.text_main_count_like)
//	val text_main_updateat = itemView.findViewById<TextView>(R.id.text_main_updateat)
//	val img_main_more = itemView.findViewById<ImageView>(R.id.img_main_more)
//
//	fun bind(
//		customData: Body,
//		position: Int,
//		homeDetailProfileAdapter: HomeDetailProfileAdapter
//	) {
//		val userImageLoad =
//			setImageURLSetting(customData.User?.Image?.src)
//		Glide.with(itemView).load(userImageLoad).into(profileImg)
//		profileName.text = customData.User?.nickname
//		text_main_updateat.text = customData.updatedAt?.let { DateParser(it).calculateDiffDate() }
//		content.text = customData.content
//		setLikeEvent(btn_favorite, customData)
//		text_main_count_image.text = customData.Images.size.toString()
//		text_main_count_comment.text = customData.Comments.size.toString()
//		text_main_count_like.text = customData.Likers.size.toString()
//
//		itemView.setOnClickListener {
//			Log.d("DetailClick",customData.id.toString())
//			//FIXME 아이디가 없는경우 존재하지 않는 글입니다 띄워주기! 그리고 자체적으로 리스트에서 삭제하고 적용하기.
//			val gotoDetailPageIntent = Intent(itemView.context, HomeDetailActivity::class.java)
//			gotoDetailPageIntent.putExtra("Detail_id", customData.id!!)
//			itemView.context.startActivity(gotoDetailPageIntent)
//		}
//		img_main_more.setOnClickListener{
//			moreEvent(it,customData)
//		}
//		btn_favorite.setOnClickListener {
//			likeClickEvent(it, customData ,position, homeDetailProfileAdapter)
//		}
//	}
//
//	private fun setLikeEvent(it: View, customData: ResponseMainItem) {
//		val boolLike = customData.Likers.any{ liker ->
//			liker.Like.UserId == App.prefs.local_user_id.toInt()
//		}
//		it.isActivated = boolLike
//	}
//
//	private fun likeClickEvent(
//		it: View,
//		customData: ResponseMainItem,
//		position: Int,
//		homeDetailProfileAdapter: HomeDetailProfileAdapter
//	) {
//		if (it.isActivated) {
//			//좋아요 버튼 눌림
//			requestToServer.service.requestDelLike(App.prefs.local_login_token, customData.id)
//				.customEnqueue { res->
//					if (res.isSuccessful) {
//						it.isActivated = false
//						text_main_count_like.text = (text_main_count_like.text.toString().toInt() - 1).toString()
//					}
//				}
//
//		} else {
//			//좋아요 안눌림
//			requestToServer.service.requestLike(App.prefs.local_login_token, customData.id)
//				.customEnqueue { res->
//					if (res.isSuccessful) {
//						it.isActivated = true
//						text_main_count_like.text = (text_main_count_like.text.toString().toInt() + 1).toString()
//					}
//				}
//		}
//	}
//
//	private fun moreEvent(it:View, customData: ResponseMainItem) {
//		if (customData.User?.id.toString() == App.prefs.local_user_id) {
//			itemView.context.customSelectDialog(View.GONE, View.VISIBLE, View.VISIBLE,
//				{},
//				{
//					Log.d("글수정 버튼", "누름")
//					fixPost(customData)
//				},
//				{
//					Log.d("글삭제 버튼", "누름")
//					requestToServer.service.requestDelete(
//						App.prefs.local_login_token,
//						customData.id
//					).customEnqueue {
//						if (it.isSuccessful) {
//							HomeFragement()
//						}
//					}
//				})
//		} else {
//			itemView.context.customSelectDialog(View.VISIBLE, View.GONE, View.GONE,
//				{
//					Log.d("글신고", "누름")
//					it.context.cumstomReportDialog{
//						requestToServer.service.requestReport(
//							RequestReport(it), customData.id)
//							.customEnqueue{ res->
//								if (res.isSuccessful) {
//									Toast
//										.makeText(itemView.context,"신고가 성공적으로 접수되었습니다.", Toast.LENGTH_SHORT)
//										.show()
//								}
//							}
//					}
//				})
//		}
//	}
//
//	private fun fixPost(customData: Body) {
//		val intent = Intent(itemView.context, AddActivity::class.java)
//		intent.putExtra("title",customData.title)
//		intent.putExtra("content",customData.content)
//		val imageArrayList = arrayListOf<String>()
//		customData.images?.forEach{
//			imageArrayList.add(it.src)
//		}
//		intent.putStringArrayListExtra("image", imageArrayList)
//		itemView.context.startActivity(intent)
//	}
//
//}