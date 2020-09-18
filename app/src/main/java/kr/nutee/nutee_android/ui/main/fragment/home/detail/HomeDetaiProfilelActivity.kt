package kr.nutee.nutee_android.ui.main.fragment.home.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.main_home_profile_detail_activity.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.App
import kr.nutee.nutee_android.data.main.RequestReport
import kr.nutee.nutee_android.data.main.home.ResponseMain
import kr.nutee.nutee_android.data.main.home.ResponseMainItem
import kr.nutee.nutee_android.data.main.profile.ResponseProfile
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.customEnqueue
import kr.nutee.nutee_android.ui.extend.dialog.cumstomReportDialog
import kr.nutee.nutee_android.ui.extend.dialog.customSelectDialog
import kr.nutee.nutee_android.ui.extend.imageSetting.setImageURLSetting
import kr.nutee.nutee_android.ui.main.fragment.home.HomeFragement

class HomeDetaiProfilelActivity : AppCompatActivity() {

	val requestToServer = RequestToServer
	private var userID:Int = 0

	private lateinit var homeDetailProfileAdapter: HomeDetailProfileAdapter
	private lateinit var contentArrayList:ResponseMain

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.main_home_profile_detail_activity)

		init()
	}

	override fun onBackPressed() {
		super.onBackPressed()
	}

	private fun init(){
		userID = intent.getIntExtra("Detail_Profile_id",0)

		img_profile_detail_back_btn.setOnClickListener {
			onBackPressed()
		}

		img_profile_detail_more.setOnClickListener {

		}
	}

	private fun setAdapter(profilePostItem: ResponseMain) {
		homeDetailProfileAdapter = HomeDetailProfileAdapter(profilePostItem, this)
		rv_detail_profile.adapter = homeDetailProfileAdapter
	}

	private fun bindUserProfile(res: ResponseProfile) {
		tv_profile_detail_nickname.text = res.nickname
		val userImageLoad = setImageURLSetting(res.Image?.src)
		Glide.with(applicationContext).load(userImageLoad).into(img_profile)
		tv_profile_detail_content_num.text = res.Posts?.size.toString()
		App.prefs.url = res.Image?.src
	}

	private fun moreEvent(it: View, customData: ResponseMainItem) {
		img_profile_detail_more.context.customSelectDialog(
			View.VISIBLE, View.GONE, View.GONE,
			{
				Log.d("글신고", "누름")
				it.context.cumstomReportDialog{
					requestToServer.service.requestReport(
						RequestReport(it), customData.id)
						.customEnqueue{ res->
							if (res.isSuccessful) {
								Toast
									.makeText(img_profile_detail_more.context,"신고가 성공적으로 접수되었습니다.", Toast.LENGTH_SHORT)
									.show()
							}
						}
				}
			})
	}


}