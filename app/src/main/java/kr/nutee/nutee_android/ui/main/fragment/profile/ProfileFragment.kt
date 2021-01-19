package kr.nutee.nutee_android.ui.main.fragment.profile


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.main_fragment_home.*
import kotlinx.android.synthetic.main.main_fragment_proflie.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.App
import kr.nutee.nutee_android.data.main.home.ResponseMain
import kr.nutee.nutee_android.data.main.profile.ResponseProfile
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.customEnqueue
import kr.nutee.nutee_android.ui.extend.dialog.customDialogSingleButton
import kr.nutee.nutee_android.ui.extend.imageSetting.setImageURLSetting
import kr.nutee.nutee_android.ui.main.fragment.home.HomeAdapter
import kr.nutee.nutee_android.ui.setting.SettingActivity

class ProfileFragment : Fragment() {

	private lateinit var homeAdapter:HomeAdapter

	override fun onCreateView(
		inflater: LayoutInflater,
		 container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.main_fragment_proflie, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		requestUserData()
	}

	private fun requestUserData() {
		RequestToServer.service
			.requestUserData(App.prefs.local_login_token)
			.customEnqueue(
				onSuccess = {response -> bindUserProfile(response.body()!!) },
				onError = {
					requireContext().customDialogSingleButton("로그인이 필요합니다!!")
				}
			)
	}

	private fun bindUserProfile(res: ResponseProfile) {
		text_user_name.text = res.nickname
		val userImageLoad = setImageURLSetting(res.Image?.src)
		Glide.with(requireContext()).load(userImageLoad).into(img_profile_image)
		text_profile_content_num.text = res.Posts?.size.toString()
		loadUserProfileList(res.id)
		App.prefs.url = res.Image?.src
	}

	private fun loadUserProfileList(id: Int) {
		RequestToServer.service
			.requestUserPosts(id)
			.customEnqueue(
				onSuccess = { response ->
					if (response.body().isNullOrEmpty()) {
						cl_profile_my_post_is_empty.visibility = View.VISIBLE
						return@customEnqueue
					}
					cl_profile_my_post_list.visibility = View.VISIBLE
					//setAdapter(response.body()!!)
				}
			)
	}

	//private fun setAdapter(mainItem: ResponseMain) {
	//	homeAdapter = HomeAdapter(mainItem, this.context!!)
	//	rv_profile_post_list.adapter = homeAdapter
	//}


}
