package kr.nutee.nutee_android.ui.main.fragment.profile


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
import kr.nutee.nutee_android.data.App
import kr.nutee.nutee_android.data.main.profile.ResponseProfile
import kr.nutee.nutee_android.databinding.MainFragmentNoticeBinding
import kr.nutee.nutee_android.databinding.MainFragmentProfileBinding
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.customEnqueue
import kr.nutee.nutee_android.ui.extend.dialog.customDialogSingleButton

class ProfileFragment : Fragment() {

	private var binding: MainFragmentProfileBinding? = null
	private lateinit var profileAdapter:ProfilePagerAdapter
	private lateinit var vp_profile: ViewPager2
	private lateinit var profileTapTextList: ArrayList<String>
	private lateinit var tab_profile: TabLayout
	private lateinit var img_profile_image: ImageView

	private lateinit var textUserName:TextView

	override fun onCreateView(
		inflater: LayoutInflater,
		 container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		//return inflater.inflate(R.layout.main_fragment_profile, container, false)
		binding = MainFragmentProfileBinding.inflate(inflater, container, false)
		return requireBinding().root
	}

	private fun requireBinding(): MainFragmentProfileBinding = binding
			?: error("binding is not init")

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		profileTapTextList = arrayListOf("게시글", "댓글", "추천 게시글")
		vp_profile = requireBinding().vpProfile
		//vp_profile = view.findViewById(R.id.vp_profile)
		tab_profile = requireBinding().tabProfile
		//tab_profile = view.findViewById(R.id.tab_profile)
		img_profile_image = requireBinding().imgProfileImage
		//img_profile_image = view.findViewById(R.id.img_profile_image)
		textUserName = requireBinding().textUserName
		//textUserName = view.findViewById(R.id.text_user_name)

		profileAdapter = ProfilePagerAdapter(this)

		requireBinding().vpProfile.adapter = profileAdapter

		// viewpager와 tablayout 연결
		TabLayoutMediator(requireBinding().tabProfile, requireBinding().vpProfile) { tab, position ->
			tab.text = profileTapTextList[position]
		}.attach()

		requestUserData()
	}

	private fun requestUserData() {
		RequestToServer.snsService
			.requestUserData("Bearer "+ App.prefs.local_login_token)
			.customEnqueue(
				onSuccess = {response -> bindUserProfile(response.body()!!) },
				onError = {
					requireContext().customDialogSingleButton("로그인이 필요합니다!!")
				}
			)
	}


	private fun bindUserProfile(res: ResponseProfile) {
		requireBinding().textUserName.text = res.body.nickname
		Glide.with(requireActivity())
				.load(res.body.image?.src)
				.placeholder(R.drawable.ic_baseline_rotate_left_24)
			.error(R.mipmap.nutee_character_background_white_round)
			.fallback(R.mipmap.nutee_character_background_white_round)
				.into(requireBinding().imgProfileImage)
	}
}
