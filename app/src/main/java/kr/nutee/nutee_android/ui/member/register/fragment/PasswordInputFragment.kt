package kr.nutee.nutee_android.ui.member.register.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.member_register_email_auth_fragment.*
import kotlinx.android.synthetic.main.member_register_password_fragment.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.ui.extend.animation.showTextShake
import kr.nutee.nutee_android.ui.extend.textChangedListener
import kr.nutee.nutee_android.ui.member.DataValid
import kr.nutee.nutee_android.ui.member.register.OnRegisterDataSetListener
import kr.nutee.nutee_android.ui.setting.TermOfUseActivity

/*
* Created by jinsu4755
* DESC: 회원가입시 비밀번호 입력을 담당하는 플래그먼트
*/

class PasswordInputFragment : Fragment(), View.OnClickListener {

	private var password: String? = null
	private var passwordCheck: String? = null

	private var onRegisterDataSetListener: OnRegisterDataSetListener? = null
	private val dataValid = DataValid()

	private var registerPasswordPreviousEvnetListener: (() -> Unit)? = null
	private var registerPasswordNextEventListener: (() -> Unit)? = null

	fun setRegisterPasswordPreviousEventListener(listener: () -> Unit) {
		this.registerPasswordPreviousEvnetListener = listener
	}

	fun setReigsterPasswordNextEventListener(listener: () -> Unit) {
		this.registerPasswordNextEventListener = listener
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		return inflater.inflate(
			R.layout.member_register_password_fragment,
			container,
			false
		)
	}

	override fun onAttach(context: Context) {
		super.onAttach(context)
		if (context !is OnRegisterDataSetListener)
			throw RuntimeException(context.toString() + "must implement OnRegisterDataSetListener")
		onRegisterDataSetListener = context
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		passwordInputButtonEventMapping()
		passwordInputButtonEnableEvent()
	}

	private fun passwordInputButtonEventMapping() {
		tv_check_agree_to_personal_info.setOnClickListener(this)
		tv_password_previous.setOnClickListener(this)
		tv_password_next.setOnClickListener(this)
	}

	private fun passwordInputButtonEnableEvent() {
		passwordTextChangedListener()
		et_register_password_check.textChangedListener {
			tv_password_next.isEnabled =
				!(it.isNullOrBlank() && password.isNullOrBlank() && passwordCheckInputEvent(it))
		}
	}

	private fun passwordTextChangedListener() {
		et_register_password.textChangedListener { pw ->
			passwordInputEvent(pw)
		}
	}

	private fun passwordInputEvent(pw: Editable?) {
		if (dataValid.isValidPassword(pw.toString())) {
			password = pw?.toString()
			tv_register_password_result.text = ""
			return
		}
		password = null
		requireContext().showTextShake(
			tv_register_password_result,
			"8자 이상의 영어 대문자, 소문자, 숫자가 포함된 비밀번호를 입력해주세요",
			R.color.colorRed
		)
	}

	private fun passwordCheckInputEvent(pw: Editable?): Boolean {
		if (pw?.toString() == password) {
			passwordCheck = pw.toString()
			tv_register_password_check_result.text = ""
			return true
		}
		passwordCheck = null
		requireContext().showTextShake(
			tv_register_password_check_result,
			"비밀번호가 일치하지 않습니다.",
			R.color.colorRed
		)
		return false
	}

	override fun onClick(passwordInputFragmentButton: View?) {
		when (passwordInputFragmentButton!!.id) {
			R.id.tv_check_agree_to_personal_info -> gotoPersonalInfoEvent()
			R.id.tv_password_previous -> registerPasswordPreviousEvnetListener?.invoke()
			R.id.tv_password_next -> {
				onRegisterDataSetListener?.onRegisterPasswordDataSetListener(password!!)
				registerPasswordNextEventListener?.invoke()
			}
		}
	}

	private fun gotoPersonalInfoEvent() {
		val gotoPersonalInfoEvent = Intent(requireContext(), TermOfUseActivity::class.java)
		startActivity(gotoPersonalInfoEvent)
	}


	override fun onDetach() {
		super.onDetach()
		onRegisterDataSetListener = null
	}
}