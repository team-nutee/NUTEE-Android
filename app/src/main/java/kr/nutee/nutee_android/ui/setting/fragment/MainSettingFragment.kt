package kr.nutee.nutee_android.ui.setting.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.setting_main_fragment.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.ui.extend.loadFragmentAddtoBackStack
import kr.nutee.nutee_android.ui.setting.DeveloperInformationActivity

/*
 * Created by eunseo5355
 * DESC: 설정 메인화면 Fragment
 */

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
			requireContext().loadFragmentAddtoBackStack(ProfileImageSettingFragment()
				,R.id.frame_layout_setting,null)
		}

		tv_setting_nickname_btn.setOnClickListener {
			requireContext().loadFragmentAddtoBackStack(NickNameSettingFragment()
				,R.id.frame_layout_setting,null)
		}

		tv_setting_password_btn.setOnClickListener {
			requireContext().loadFragmentAddtoBackStack(PasswordSettingFragment()
				,R.id.frame_layout_setting,null)
		}

		tv_setting_category_btn.setOnClickListener {
			requireContext().loadFragmentAddtoBackStack(CategorySettingFragment()
				,R.id.frame_layout_setting,null)
		}

		tv_setting_major_btn.setOnClickListener {
			requireContext().loadFragmentAddtoBackStack(MajorSettingFragment()
				,R.id.frame_layout_setting,null)
		}

		tv_setting_developer_information_btn.setOnClickListener {
			val intent = Intent(context, DeveloperInformationActivity::class.java)
			startActivity(intent)
		}

	}

}