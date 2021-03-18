package kr.nutee.nutee_android.ui.main.fragment.profile


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.R.*
import kr.nutee.nutee_android.data.App
import kr.nutee.nutee_android.data.TestToken
import kr.nutee.nutee_android.data.main.profile.ResponseProfile
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.customEnqueue
import kr.nutee.nutee_android.ui.extend.dialog.customDialogSingleButton
import kr.nutee.nutee_android.ui.extend.imageSetting.setImageURLSetting

class ProfileFragment : Fragment() {

	private lateinit var profileAdapter:ProfilePagerAdapter
	private lateinit var vp_profile: ViewPager2
	private lateinit var profileTapTextList: ArrayList<String>
	private lateinit var tab_profile: TabLayout
	private lateinit var img_profile_image: ImageView
	private lateinit var tab1: LayoutInflater
	private lateinit var tab2: LayoutInflater
	private lateinit var tab3: LayoutInflater

	val text_user_name = view?.findViewById<TextView>(R.id.text_user_name)

	override fun onCreateView(
		inflater: LayoutInflater,
		 container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.main_fragment_profile, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		profileTapTextList = arrayListOf("내가 쓴 글", "내가 쓴 댓글", "내가 추천한 글")
		vp_profile = view.findViewById(R.id.vp_profile)
		tab_profile = view.findViewById(R.id.tab_profile)
		img_profile_image = view.findViewById(R.id.img_profile_image)
		profileAdapter = ProfilePagerAdapter(this)

		vp_profile.adapter = profileAdapter

		// viewpager와 tablayout 연결
		TabLayoutMediator(tab_profile, vp_profile) { tab, position ->
			tab.text = profileTapTextList[position]
		}.attach()

		requestUserData()
	}

	private fun requestUserData() {
		RequestToServer.backService
			.requestUserData("Bearer "+ TestToken.testToken)//App.prefs.local_login_token)
			.customEnqueue(
				onSuccess = {response -> bindUserProfile(response.body()!!) },
				onError = {
					requireContext().customDialogSingleButton("로그인이 필요합니다!!")
				}
			)
	}

	private fun bindUserProfile(res: ResponseProfile) {
		text_user_name?.text = res.body.nickname
		val userImageLoad = setImageURLSetting(res.body.image.src)
		Glide.with(requireContext()).load(userImageLoad).into(img_profile_image)
		App.prefs.url = res.body.image.src
	}

//	private fun loadUserProfileList(id: Int) {
//		RequestToServer.service
//			.requestUserPosts(id)
//			.customEnqueue(
//				onSuccess = { response ->
//					if (response.body().isNullOrEmpty()) {
//						cl_profile_my_post_is_empty.visibility = View.VISIBLE
//						return@customEnqueue
//					}
//					cl_profile_my_post_list.visibility = View.VISIBLE
//				}
//			)
//	}


}
