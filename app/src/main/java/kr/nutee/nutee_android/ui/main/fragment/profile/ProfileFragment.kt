package kr.nutee.nutee_android.ui.main.fragment.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.main_fragment_proflie.*

import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.App
import kr.nutee.nutee_android.data.member.login.ResponseLogin
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.customEnqueue
import kr.nutee.nutee_android.ui.extend.imageSetting.imageSetting

class ProfileFragment : Fragment() {

	val requestToServer = RequestToServer

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
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
		requestToServer.service.requestUserData(App.prefs.local_login_token).customEnqueue {
			if (it.isSuccessful) {
				it.body()?.let { result -> bindUserProfile(result)} }
		}
	}

	private fun bindUserProfile(res: ResponseLogin) {
		text_user_name.text = res.nickname
		val userImageLoad =
			imageSetting(res.Image)
		context?.let { Glide.with(it).load(userImageLoad).into(img_profile_image) }
		text_profile_content_num.text = res.Posts.size.toString()
	}


}
