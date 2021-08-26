package kr.nutee.nutee_android.ui.setting.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.setting_activity.*
import kotlinx.android.synthetic.main.setting_main_fragment.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.App
import kr.nutee.nutee_android.databinding.MemberRegisterSelectDepartmentFragmentBinding
import kr.nutee.nutee_android.databinding.SettingActivityBinding
import kr.nutee.nutee_android.databinding.SettingMainFragmentBinding
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.customEnqueue
import kr.nutee.nutee_android.ui.extend.loadFragmentAddtoBackStack
import kr.nutee.nutee_android.ui.member.LoginActivity
import kr.nutee.nutee_android.ui.setting.DeveloperInformationActivity
import kr.nutee.nutee_android.ui.setting.SettingActivity

/*
 * Created by eunseo5355
 * DESC: 설정 메인화면 Fragment
 *
 * Created by 88yhtserof
 * 누티 2.0 리팩토링
 */

class MainSettingFragment : Fragment() {

	private var binding: SettingMainFragmentBinding?= null

	override fun onCreateView(
			inflater: LayoutInflater,
			container: ViewGroup?,
			savedInstanceState: Bundle?
	): View? {
		binding = SettingMainFragmentBinding.inflate(inflater, container, false)
		return requireBinding().root
		//return inflater.inflate(R.layout.setting_main_fragment,container,false)
	}

	private fun requireBinding(): SettingMainFragmentBinding = binding
			?: error("binding is not init")

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		val binding by lazy { SettingActivityBinding.inflate(layoutInflater) }

		//프로필 이미지 설정
		requireBinding().tvSettingProfileImageBtn.setOnClickListener {
			requireContext().loadFragmentAddtoBackStack(ProfileImageSettingFragment()
					,binding.frameLayoutSetting.id,null)
		}
		/*tv_setting_profile_image_btn.setOnClickListener {
			requireContext().loadFragmentAddtoBackStack(ProfileImageSettingFragment()
					,R.id.frame_layout_setting,null)
		}*/

		//닉네임 설정
		requireBinding().tvSettingNicknameBtn.setOnClickListener {
			requireContext().loadFragmentAddtoBackStack(NickNameSettingFragment()
					,binding.frameLayoutSetting.id,null)
		}
		/*tv_setting_nickname_btn.setOnClickListener {
			requireContext().loadFragmentAddtoBackStack(NickNameSettingFragment()
					,R.id.frame_layout_setting,null)
		}*/

		//비밀번호 재설정
		requireBinding().tvSettingPasswordBtn.setOnClickListener {
			requireContext().loadFragmentAddtoBackStack(PasswordSettingFragment()
					,binding.frameLayoutSetting.id,null)
		}
		/*tv_setting_password_btn.setOnClickListener {
			requireContext().loadFragmentAddtoBackStack(PasswordSettingFragment()
					,R.id.frame_layout_setting,null)
		}*/

		//카테고리 재설정
		requireBinding().tvSettingCategoryBtn.setOnClickListener {
			requireContext().loadFragmentAddtoBackStack(CategorySettingFragment()
					,binding.frameLayoutSetting.id,null)
		}
		/*tv_setting_category_btn.setOnClickListener {
			requireContext().loadFragmentAddtoBackStack(CategorySettingFragment()
					,R.id.frame_layout_setting,null)
		}*/

		//전공 재설정
		requireBinding().tvSettingMajorBtn.setOnClickListener {
			requireContext().loadFragmentAddtoBackStack(MajorSettingFragment()
					,binding.frameLayoutSetting.id,null)
		}
		/*tv_setting_major_btn.setOnClickListener {
			requireContext().loadFragmentAddtoBackStack(MajorSettingFragment()
					,R.id.frame_layout_setting,null)
		}*/

		with(requireBinding()){
			//개발자 정보
			tvSettingDeveloperInformationBtn.setOnClickListener {
				val intent = Intent(context, DeveloperInformationActivity::class.java)
				startActivity(intent)
			}

			//로그아웃
			tvSettingLogout.setOnClickListener {
				requestlogout()
			}
		}

	}

	private fun requestlogout(){
		RequestToServer.authService
				.reqeustLogout(App.prefs.local_login_token)
				.customEnqueue(
						onSuccess = {
							App.prefs.local_login_id = ""
							App.prefs.local_login_pw = ""
							App.prefs.local_login_token = ""
							App.prefs.local_login_oto=false

							Toast.makeText(view?.context,it.body()!!.body, Toast.LENGTH_LONG).show()
							val intent = Intent(view?.context, LoginActivity::class.java)
							startActivity(intent)
							activity?.finish()
						}
				)
	}

}