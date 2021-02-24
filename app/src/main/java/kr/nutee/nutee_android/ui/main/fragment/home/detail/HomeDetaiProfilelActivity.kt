package kr.nutee.nutee_android.ui.main.fragment.home.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.main_home_profile_detail_activity.*
import kotlinx.android.synthetic.main.user_find_activity.view.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.App
import kr.nutee.nutee_android.data.main.RequestReport
import kr.nutee.nutee_android.data.main.home.ResponseMain
import kr.nutee.nutee_android.data.main.profile.ResponseProfile
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.customEnqueue
import kr.nutee.nutee_android.ui.extend.dialog.cumstomReportDialog
import kr.nutee.nutee_android.ui.extend.dialog.customSelectDialog
import kr.nutee.nutee_android.ui.extend.imageSetting.setImageURLSetting

class HomeDetaiProfilelActivity : AppCompatActivity() {

	val requestToServer = RequestToServer
	private var userID:Int = 0

	//private lateinit var homeDetailProfileAdapter: HomeDetailProfileAdapter
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
		loadUserProfile(userID)

		loadUserPost(userID){
			contentArrayList = it
			setAdapter(it)
		}

		img_profile_detail_back_btn.setOnClickListener {
			onBackPressed()
		}

	}

	private fun loadUserProfile(id: Int) {
		requestToServer.authService
			.requestUserProfile(id)
			.customEnqueue(
				onSuccess ={
					bindUserProfile(id, it.body()!!)
				},
				onError = {}
			)
	}

	private fun loadUserPost(id: Int, loadfun:(resMain:ResponseMain)->Unit) {
		requestToServer.authService
			.requestUserPosts(id)
			.customEnqueue(
				onSuccess = {
					loadfun(it.body()!!)},
				onError = {}
			)
	}

	private fun setAdapter(profilePostItem: ResponseMain) {
		//homeDetailProfileAdapter = HomeDetailProfileAdapter(profilePostItem, this)
		//rv_detail_profile.adapter = homeDetailProfileAdapter
	}

	private fun bindUserProfile(userID: Int, res: ResponseProfile) {
		tv_profile_detail_nickname.text = res.body.nickname
		val userImageLoad = setImageURLSetting(res.body.profileUrl.src)
		Glide.with(applicationContext).load(userImageLoad).into(img_profile)
		App.prefs.url = res.body.profileUrl.src

		img_profile_detail_more.setOnClickListener {
			profileMore(userID, res)
		}
	}

	private fun profileMore(userID: Int, customData: ResponseProfile) {
		if (userID.toString() == App.prefs.local_user_id) {
			customSelectDialog(View.GONE,View.GONE, View.VISIBLE, View.VISIBLE,
				{},{},
				{
					Log.d("글수정 버튼", "누름")
				},
				{
					Log.d("글삭제 버튼", "누름")
					RequestToServer.backService.requestDelete(
						App.prefs.local_login_token,
						customData.body.id)
						.customEnqueue(
							onSuccess = {finish()},
							onError = {}
						)
				})
		} else {
			customSelectDialog(View.VISIBLE, View.GONE,View.GONE, View.GONE,
				{
					Log.d("글신고", "누름")
					cumstomReportDialog("이 게시글을 신고하시겠습니까?"){
						RequestToServer.backService.requestReport(
							RequestReport(it), customData.body.id)
							.customEnqueue(
								onSuccess = {
									Toast
										.makeText(applicationContext,"신고가 성공적으로 접수되었습니다.", Toast.LENGTH_SHORT)
										.show()
								},
								onError = {}
							)
					}
				}
			)
		}
	}


}