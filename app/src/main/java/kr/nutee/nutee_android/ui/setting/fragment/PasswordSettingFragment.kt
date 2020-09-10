package kr.nutee.nutee_android.ui.setting.fragment

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.setting_password_fragment.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.App
import kr.nutee.nutee_android.data.ValidData
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.animation.showTextShake
import kr.nutee.nutee_android.ui.extend.customEnqueue
import kr.nutee.nutee_android.ui.extend.loadFragment
import kr.nutee.nutee_android.ui.extend.textChangedListener

/*
 * Created by eunseo5355
 * DESC: 비밀번호 설정 Fragment
 */

class PasswordSettingFragment : Fragment() {

	val validData = ValidData()

	var validPassword: String? = null
	var checkedPassword: String? = null

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		return inflater.inflate(R.layout.setting_password_fragment, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		init()
	}

	private fun init() {
		passwordSettingEnableEvnet()
		passwordSettingButtonEvent()
	}

	private fun savebuttonEnable(editable: Editable?) {
		tv_setting_password_save_btn.isEnabled =
			!editable.isNullOrBlank() && !validPassword.isNullOrBlank() && !checkedPassword.isNullOrBlank()
	}

	private fun passwordSettingEnableEvnet() {
		et_setting_password.textChangedListener {
			checkValidPassowrd(it)
			savebuttonEnable(it)
		}
		et_setting_password_check.textChangedListener {
			checkPasswordIsEquals(it)
			savebuttonEnable(it)
		}
	}

	private fun checkValidPassowrd(password: Editable?) {
		if (validData.isValidPassword(password.toString())) {
			requireContext().showTextShake(
				tv_setting_password_result,
				"사용 가능한 비밀번호 입니다.",
				R.color.nuteeBase
			)
			validPassword = password.toString()
			return
		}
		requireContext().showTextShake(
			tv_setting_password_result,
			"8자 이상의 !@#\$%^&*_+- 가 포함된\n" +
					" 영어 대문자, 소문자, 특수문자, 숫자가 포함된 비밀번호를 입력해주세요",
			R.color.colorRed
		)
	}
	
	private fun checkPasswordIsEquals(password: Editable?){
		if (password.toString() == validPassword) {
			checkedPassword = et_setting_password_check.text.toString()
			requireContext().showTextShake(
				tv_setting_password_check_result,
				"사용 가능한 비밀번호 입니다.",
				R.color.nuteeBase
			)
			return
		}
		requireContext().showTextShake(
			tv_setting_password_check_result,
			"비밀번호가 일치하지 않습니다.",
			R.color.colorRed
		)
	}

	private fun passwordSettingButtonEvent() {
		tv_setting_password_save_btn.setOnClickListener {
			requestToChangePassword()
		}
	}

	private fun requestToChangePassword(){
		RequestToServer.service
			.requestChagePassword(
				cookie = App.prefs.local_login_token,
				newpassword = validPassword!!
			)
			.customEnqueue(
				onSuccess = {
					requireContext().loadFragment(MainSettingFragment(),R.id.frame_layout_setting)
					Toast.makeText(
						requireContext(),
						"비밀번호 변경되었습니다",
						Toast.LENGTH_SHORT
					).show()
				},
				onError = {
					Toast.makeText(
						requireContext(),
						"비밀번호 변경에 실패하였습니다.",
						Toast.LENGTH_SHORT
					).show()
				}
			)
	}
}