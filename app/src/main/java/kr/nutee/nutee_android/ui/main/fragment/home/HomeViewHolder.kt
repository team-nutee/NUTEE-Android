package kr.nutee.nutee_android.ui.main.fragment.home

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.main_fragment_home.view.*
import kotlinx.android.synthetic.main.term_of_use_activity.view.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.App
import kr.nutee.nutee_android.data.DateParser
import kr.nutee.nutee_android.data.main.RequestReport
import kr.nutee.nutee_android.data.main.home.ResponseMainItem
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.cumstomReportDialog
import kr.nutee.nutee_android.ui.extend.customEnqueue
import kr.nutee.nutee_android.ui.extend.customSelectDialog
import kr.nutee.nutee_android.ui.extend.imageSetting
import kr.nutee.nutee_android.ui.main.MainActivity
import kr.nutee.nutee_android.ui.main.fragment.home.detail.HomeDetailFragment
import kr.nutee.nutee_android.ui.member.LoginActivity


/*home fragment RecyclerView 내부 하나의 뷰의 정보를 지정하는 클래스 */
class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

	val requestToServer = RequestToServer

	val profileImg = itemView.findViewById<ImageView>(R.id.img_list_profile)
	val profileName = itemView.findViewById<TextView>(R.id.text_main_username)
	val content = itemView.findViewById<TextView>(R.id.text_main_content)
	val btn_favorite = itemView.findViewById<ImageView>(R.id.img_main_count_like)
	val text_main_count_image = itemView.findViewById<TextView>(R.id.text_main_count_image)
	val text_main_count_comment = itemView.findViewById<TextView>(R.id.text_main_count_comment)
	val text_main_count_like = itemView.findViewById<TextView>(R.id.text_main_count_like)
	val text_main_updateat = itemView.findViewById<TextView>(R.id.text_main_updateat)
	val img_main_more = itemView.findViewById<ImageView>(R.id.img_main_more)

	fun bind(customData: ResponseMainItem) {
		val userImageLoad = imageSetting(customData.User.Image?.src)
		Glide.with(itemView).load(userImageLoad).into(profileImg)
		profileName.text = customData.User.nickname
		text_main_updateat.text = customData.updatedAt?.let { DateParser(it).calculateDiffDate() }
		content.text = customData.content
		setLikeEvent(btn_favorite,customData)
		text_main_count_image.text = customData.Images.size.toString()
		text_main_count_comment.text = customData.Comments.size.toString()
		text_main_count_like.text = customData.Likers.size.toString()

		btn_favorite.setOnClickListener{ likeClickEvent(it,customData) }
		itemView.setOnClickListener{
			Log.d("DetailClick",customData.id.toString())
			val transaction = (itemView.context as MainActivity).supportFragmentManager.beginTransaction()
			transaction
				.replace(R.id.frame_layout, HomeDetailFragment(customData.id!! + 1))
				.addToBackStack(null)
				.commit()
		}
		img_main_more.setOnClickListener{
			moreEvent(it,customData)
		}
	}

	private fun setLikeEvent(it: View, customData: ResponseMainItem) {
		val boolLike = customData.Likers.any{ liker ->
			liker.Like.UserId == App.prefs.local_user_id.toInt()
		}

		it.isActivated = boolLike

	}

	private fun likeClickEvent(it: View, customData: ResponseMainItem) {
		if (it.isActivated) {
			//좋아요 버튼 활성상태
			requestToServer.service.requestDelLike(App.prefs.local_login_token, customData.id)
				.customEnqueue { res->
					if (res.isSuccessful) {
						it.isActivated = !it.isActivated
					}
				}

		} else {
			//비활성 상태
			requestToServer.service.requestLike(App.prefs.local_login_token, customData.id)
				.customEnqueue { res->
					if (res.isSuccessful) {
						it.isActivated = !it.isActivated
					}
				}
		}
	}

	private fun moreEvent(it:View, customData: ResponseMainItem) {
		if (customData.User.id.toString() == App.prefs.local_user_id) {
			itemView.context.customSelectDialog(View.GONE, View.VISIBLE, View.VISIBLE,
				{},
				{
					Log.d("글수정 버튼", "누름")
				},
				{
					Log.d("글삭제 버튼", "누름")
					requestToServer.service.requestDelete(
						App.prefs.local_login_token,
						customData.id
					).customEnqueue {
						if (it.isSuccessful) {
							HomeFlagement()
						}
					}
				})
		} else {
			itemView.context.customSelectDialog(View.VISIBLE, View.GONE, View.GONE,
				{
					Log.d("글신고", "누름")
					it.context.cumstomReportDialog{
						requestToServer.service.requestReport(
							RequestReport(it), customData.id)
							.customEnqueue{ res->
								if (res.isSuccessful) {
									Toast
										.makeText(itemView.context,"신고가 성공적으로 접수되었습니다.",Toast.LENGTH_SHORT)
										.show()
								}
							}
					}
				})
		}
	}

}

