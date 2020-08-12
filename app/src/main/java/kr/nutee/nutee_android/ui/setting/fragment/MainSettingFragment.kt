package kr.nutee.nutee_android.ui.setting.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.setting_main_fragment.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.ui.extend.loadFragment

class MainSettingFragment : Fragment() {

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		return inflater.inflate(R.layout.setting_main_fragment,container,false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		tv_setting_profile_image_btn.setOnClickListener {
			requireContext().loadFragment(ProfileImageSettingFragment(),R.id.frame_layout_setting)
		}

		tv_setting_nickname_btn.setOnClickListener {
			requireContext().loadFragment(NickNameSettingFragment(),R.id.frame_layout_setting)
		}

		tv_setting_password_btn.setOnClickListener {
			requireContext().loadFragment(PasswordSettingFragment(),R.id.frame_layout_setting)
		}

		tv_setting_category_btn.setOnClickListener {
			requireContext().loadFragment(CategorySettingFragment(),R.id.frame_layout_setting)
		}

		tv_setting_major_btn.setOnClickListener {
			requireContext().loadFragment(MajorSettingFragment(),R.id.frame_layout_setting)
		}

	}





}