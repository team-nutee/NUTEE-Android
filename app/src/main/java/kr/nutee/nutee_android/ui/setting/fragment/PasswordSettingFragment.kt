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
import kr.nutee.nutee_android.data.main.setting.RequestChangePassword
import kr.nutee.nutee_android.databinding.SettingNicknameFragmentBinding
import kr.nutee.nutee_android.databinding.SettingPasswordFragmentBinding
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.animation.showTextShake
import kr.nutee.nutee_android.ui.extend.customEnqueue
import kr.nutee.nutee_android.ui.extend.dialog.customDialogSingleButton
import kr.nutee.nutee_android.ui.extend.loadFragment
import kr.nutee.nutee_android.ui.extend.textChangedListener

/*
 * Created by eunseo5355
 * DESC: 비밀번호 설정 Fragment
 */

class PasswordSettingFragment : Fragment() {

	private var binding: SettingPasswordFragmentBinding? = null

	val validData = ValidData()

	var validPassword: String? = null
	var checkedPassword: String? = null

	override fun onCreateView(
			inflater: LayoutInflater,
			container: ViewGroup?,
			savedInstanceState: Bundle?
	): View? {
		binding = SettingPasswordFragmentBinding.inflate(inflater, container, false)
		return requireBinding().root
	}

	private fun requireBinding(): SettingPasswordFragmentBinding = binding
		?: error("binding is not init")

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		init()
	}

	private fun init() {
		passwordSettingEnableEvnet()
		passwordSettingButtonEvent()
	}

	private fun savebuttonEnable(editable: Editable?) {
		with(requireBinding()){
			tvSettingPasswordSaveBtn.isEnabled =
				!editable.isNullOrBlank() && !validPassword.isNullOrEmpty() && !checkedPassword.isNullOrBlank()
		}
	}

	private fun passwordSettingEnableEvnet() {
		with(requireBinding()){
			etSettingPassword.textChangedListener {
				checkValidPassowrd(it)
				savebuttonEnable(it)
			}
			etSettingPasswordCheck.textChangedListener {
				checkPasswordIsEquals(it)
				savebuttonEnable(it)
			}
		}
	}

	private fun checkValidPassowrd(password: Editable?) {
		with(requireBinding()){
			if (validData.isValidPassword(password.toString())){
				requireContext().showTextShake(
					tvSettingPasswordResult,
					"사용 가능한 비밀번호 입니다.",
					R.color.nuteeBase
				)
				validPassword = password.toString()
				return
			}
			requireContext().showTextShake(
				tvSettingPasswordResult,
				"8자 이상의 !@#\$%^&*_+- 가 포함된\n" +
						" 영어 대문자, 소문자, 특수문자, 숫자가 포함된 비밀번호를 입력해주세요",
				R.color.colorRed
			)
		}
	}

	private fun checkPasswordIsEquals(password: Editable?){
		with(requireBinding()){
			if (password.toString() == validPassword) {
				checkedPassword = etSettingPasswordCheck.text.toString()
				requireContext().showTextShake(
					tvSettingPasswordCheckResult,
					"사용 가능한 비밀번호 입니다.",
					R.color.nuteeBase
				)
				return
			}
			requireContext().showTextShake(
				tvSettingPasswordCheckResult,
				"비밀번호가 일치하지 않습니다.",
				R.color.colorRed
			)
		}
	}

	private fun passwordSettingButtonEvent() {
		requireBinding().tvSettingPasswordSaveBtn.setOnClickListener {
			requestToChangePassword()
		}
	}

	private fun requestToChangePassword(){
		RequestToServer.authService
				.requestChagePassword(
						"Bearer "+ App.prefs.local_login_token,
						RequestChangePassword(
								App.prefs.local_login_pw,
								validPassword!!
						)
				).customEnqueue(
						onSuccess = {
							context?.customDialogSingleButton(getString(R.string.changeSuccess)) {}
						},
						onError = {
							context?.customDialogSingleButton(getString(R.string.changeError)) {}
						}
				)
	}
}