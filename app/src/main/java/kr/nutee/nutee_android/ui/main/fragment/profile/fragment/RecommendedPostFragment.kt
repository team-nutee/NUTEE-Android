package kr.nutee.nutee_android.ui.main.fragment.profile.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.main_fragment_profile_recommended_post.*
import kotlinx.android.synthetic.main.main_fragment_profile.*
import kotlinx.android.synthetic.main.notice_fragment_bachelor.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.App
import kr.nutee.nutee_android.data.main.home.Notice
import kr.nutee.nutee_android.data.main.home.ResponseMain
import kr.nutee.nutee_android.data.main.profile.ResponseProfile
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.customEnqueue
import kr.nutee.nutee_android.ui.extend.dialog.customDialogSingleButton
import kr.nutee.nutee_android.ui.extend.imageSetting.setImageURLSetting
import kr.nutee.nutee_android.ui.main.fragment.home.HomeAdapter
import kr.nutee.nutee_android.ui.main.fragment.notice.NoticeRecyclerAdapter

class RecommendedPostFragment: Fragment() {

	private lateinit var homeAdapter:HomeAdapter

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.main_fragment_profile_recommended_post, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		rv_profile_post_list.layoutManager = LinearLayoutManager(this.context,
			LinearLayoutManager.VERTICAL, false)
		rv_profile_post_list.setHasFixedSize(true)

		requestUserData()

	}

	private fun requestUserData() {
		RequestToServer.service
			.requestUserData(App.prefs.local_login_token)
			.customEnqueue(
				onSuccess = {response -> loadUserProfileList(response.body()!!) },
				onError = {
					requireContext().customDialogSingleButton("로그인이 필요합니다!!")
				}
			)
	}

	private fun loadUserProfileList(res: ResponseProfile) {
		RequestToServer.service
			.requestUserPosts(res.id)
			.customEnqueue(
				onSuccess = { response ->
					if (response.body().isNullOrEmpty()) {
						cl_my_profile_recommended_post_is_empty.visibility = View.VISIBLE
						return@customEnqueue
					}
					cl_my_profile_recommended_post_list.visibility = View.VISIBLE
					// loadRecommendedPost{}
				}
			)
	}

	/*private fun loadRecommendedPost(function: (resRecommendedPost: ResponseMain) -> kotlin.Unit) {
		RequestToServer.
		homeAdapter = HomeAdapter(, this.context!!)
		rv_profile_post_list.adapter = homeAdapter
	}

	private fun loadBachelor(function: (resBachelor:Notice) -> Unit) {
		requestToServer.noticeService.requestBachelor(
		).customEnqueue { response ->
			response.body()?.let {
				rv_notice_bachelor.adapter = NoticeRecyclerAdapter(this.context!!, it)
			}
		}
	}
*/
}